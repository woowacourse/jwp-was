package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class HttpRequestTest {

    @Test
    @DisplayName("HTTP 요청으로부터 HttpRequest 객체 생성하기 HttpRequest 객체 생성하기")
    void readHttpRequest() throws IOException {
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n\n"
            + "id=3456789";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        HttpRequest httpRequest = HttpRequest.readHttpRequest(testInput);

        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getUriPath()).isEqualTo("/join");
        assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("10");
    }

    @Test
    @DisplayName("HTTP 요청으로부터 HttpRequest 객체 생성하기 - request line 형식이 잘못된 경우 예외처리")
    void readHttpRequest_IfRequestLineFormatIsWring_ThrowException() {
        String httpRequestFormat = "GGGGET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n\n"
            + "id=3456789";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        assertThatThrownBy(() -> HttpRequest.readHttpRequest(testInput))
            .isInstanceOf(Exception.class)
            .hasMessage("request line input is unformatted.");
    }

    @Test
    @DisplayName("HTTP 요청으로부터 HttpRequest 객체 생성하기 - header 형식이 잘못된 경우 예외처리")
    void readHttpRequest_IfRequestHeaderFormatIsWring_ThrowException() {
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host; localhost:8080\n\n";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        assertThatThrownBy(() -> HttpRequest.readHttpRequest(testInput))
            .isInstanceOf(Exception.class)
            .hasMessage("input header format : Host; localhost:8080 is wrong.");
    }

    @Test
    @DisplayName("HTTP 요청으로부터 HttpRequest 객체 생성하기 - request 마지막에 있어야하는 공백라인이 없을 때 예외처리")
    void readHttpRequest_IfEndSpaceIsNotExist_ThrowException() {
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        assertThatThrownBy(() -> HttpRequest.readHttpRequest(testInput))
            .isInstanceOf(Exception.class)
            .hasMessage("an empty line must exist at the end of the request.");
    }

    @ParameterizedTest
    @CsvSource({"Content-Length,True", "X-Auth-Token,False"})
    @DisplayName("요청 헤더 문자열에 특정 헤더가 존재하는지 아닌지 알아보기")
    void isExistRequestHeader(String headerName, boolean expected) {
        String requestHeaderWithContentLength = "GET /user/create?id=1 HTTP/1.1\n"
            + "Content-Length: 10\n";

        assertThat(HttpRequest.isExistRequestHeader(requestHeaderWithContentLength, headerName))
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("헤더에서 특정 필드 값 찾기")
    void findHeaderValue() {
        String requestHeaderWithContentLength = "GET /user/create?id=1 HTTP/1.1\n"
            + "Content-Length: 250\n";
        assertThat(HttpRequest.findHeaderValue(requestHeaderWithContentLength, "Content-Length"))
            .isEqualTo("250");
    }

    @Test
    @DisplayName("헤더에서 특정 필드 값 찾기 - 존재하지 않는 필드에 대한 예외처리")
    void findHeaderValue_IfFieldIsNotExist_ThrowException() {
        String requestWithoutContentLength = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n";
        assertThatThrownBy(() -> HttpRequest.findHeaderValue(
            requestWithoutContentLength, "Content-Length"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this header field does not exist.");
    }

    @ParameterizedTest
    @MethodSource("getParameterOfIsRequestLineUriQueryString")
    @DisplayName("요청 헤더의 URI 가 query string 형식인지 아닌지 알아보기")
    void isRequestLineUriQueryString(String requestLine, boolean expected) {
        HttpRequest httpRequest = new HttpRequest(requestLine, "");

        assertThat(httpRequest.isRequestLineUriQueryString()).isEqualTo(expected);
    }

    private static Stream<Arguments> getParameterOfIsRequestLineUriQueryString() {
        return Stream.of(
            Arguments.of("GET /join?id=1 HTTP/1.1\n", true),
            Arguments.of("GET /join HTTP/1.1\n", false),
            Arguments.of("POST /join?id=1 HTTP/1.1\n", true),
            Arguments.of("POST /join HTTP/1.1\n", false)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /user/create?id=1 HTTP/1.1\n",
        "POST /user/create?id=1 HTTP/1.1\n"})
    @DisplayName("요청 헤더의 URI 로부터 query 데이터 가져오기")
    void getQueryDataFromUri(String requestHeader) {
        HttpRequest httpRequest = new HttpRequest(requestHeader, "");

        Map<String, String> expected = new HashMap<>();
        expected.put("id", "1");

        assertThat(httpRequest.getQueryDataFromUri()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET /user/create HTTP/1.1\n", "PUT /user/create HTTP/1.1\n"})
    @DisplayName("요청 헤더의 uri로부터 query data 가져오기 - uri가 query string 형식이 아닐때 예외처리")
    void getQueryDataFromUri_IfUriIsNotQueryString_ThrowException(String requestHeader) {
        HttpRequest httpRequest = new HttpRequest(requestHeader, "");

        assertThatThrownBy(httpRequest::getQueryDataFromUri).isInstanceOf(RequestDataFormatException.class);
    }

    @Test
    @DisplayName("요청의 body 로부터 form 데이터 얻기")
    void getValueFromFormData() {
        String requestHeader = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.getValueFromFormData("id")).isEqualTo("3456789");
    }

    @Test
    @DisplayName("요청의 body 로부터 form 데이터 얻기 - body가 비어있는 경우 예외처리")
    void getValueFromFormData_IfMessageBodyIsEmpty_ThrowException() {
        String requestHeader = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        HttpRequest httpRequest = new HttpRequest(requestHeader, "");

        assertThatThrownBy(() -> httpRequest.getValueFromFormData("Host"))
            .isInstanceOf(RequestDataFormatException.class)
            .hasMessage("message body is empty.");
    }

    @Test
    @DisplayName("요청의 body 로부터 form 데이터 얻기 - body가 form data 형식이 아닌 경우 예외처리")
    void getValueFromFormData_IfRequestHasNotFormData_ThrowException() {
        String requestHeader = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "01234";

        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThatThrownBy(() -> httpRequest.getValueFromFormData("Host"))
            .isInstanceOf(RequestDataFormatException.class)
            .hasMessage("message body is not form data format.");
    }

    @Test
    @DisplayName("uri path 알아보기")
    void isUriPath() {
        String requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.isUriPath("/join")).isTrue();
        assertThat(httpRequest.isUriPath("/join?")).isFalse();
    }

    @Test
    @DisplayName("요청의 method 알아보기")
    void isMethod() {
        String requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.isMethod(Method.GET)).isTrue();
        assertThat(httpRequest.isMethod(Method.POST)).isFalse();
    }

    @Test
    @DisplayName("요청의 method 꺼내기")
    void getMethod() {
        String requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청의 uri path 꺼내기")
    void getUriPath() {
        String requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.getUriPath()).isEqualTo("/join");
    }

    @Test
    @DisplayName("요청의 특정 헤더 값 꺼내기")
    void getHeaderValue() {
        String requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.getHeader("Host"))
            .isEqualTo("localhost:8080");
    }

    @Test
    @DisplayName("요청의 특정 헤더 값 꺼내기 - 해당 헤더 필드가 없는 경우 예외처리")
    void getHeaderValue_IfCorrespondingHeaderIsNotExist_ThrowException() {
        String requestHeader = "GET /join?id=1 HTTP/1.1\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n";
        String requestBody = "id=3456789";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThatThrownBy(() -> httpRequest.getHeader("Host"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this header field does not exist.");
    }
}
