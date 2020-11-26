package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class HttpRequestTest {

    @ParameterizedTest
    @MethodSource("testcaseForReadHttpRequest")
    @DisplayName("HTTP 요청으로부터 HttpRequest 객체 생성하기")
    void readHttpRequest(String httpRequestFormat, String expectedMethod, String expectedUriPath,
            String expectedContentLength) throws IOException {
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());
        HttpRequest httpRequest = HttpRequest.readHttpRequest(testInput);

        assertThat(httpRequest.getMethod()).isEqualTo(expectedMethod);
        assertThat(httpRequest.getUriPath()).isEqualTo(expectedUriPath);
        assertThat(httpRequest.getHeader("Content-Length"))
            .isEqualTo(expectedContentLength);
    }

    private static Stream<Arguments> testcaseForReadHttpRequest() {
        return Stream.of(
            Arguments.of(
                "GET /join?id=1 HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Cache-Control: max-age=0\n"
                + "Upgrade-Insecure-Requests: 1\n"
                + "Content-Length: 10\n\n"
                + "id=3456789",
                "GET",
                "/join",
                "10"
            ),
            Arguments.of(
                "POST /join?id=1 HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Cache-Control: max-age=0\n"
                + "Upgrade-Insecure-Requests: 1\n"
                + "Content-Length: 11\n\n"
                + "id=3456789s",
                "POST",
                "/join",
                "11"
            )
        );
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

    @Test
    @DisplayName("요청의 body 로부터 form 데이터 얻기")
    void getValueFromFormData() {
        String requestHeader = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n"
            + "Content-Type: application/x-www-form-urlencoded\n";
        String requestBody = "userId=javajigi&password=password&name=JaeSung";
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.getValueFromFormData("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getValueFromFormData("password")).isEqualTo("password");
        assertThat(httpRequest.getValueFromFormData("name")).isEqualTo("JaeSung");
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

    @ParameterizedTest
    @MethodSource("testcaseForIsMethod")
    @DisplayName("요청의 method 알아보기")
    void isMethod(String requestHeader, String requestBody, boolean isGet, boolean isPost) {
        HttpRequest httpRequest = new HttpRequest(requestHeader, requestBody);

        assertThat(httpRequest.isMethod(Method.GET)).isEqualTo(isGet);
        assertThat(httpRequest.isMethod(Method.POST)).isEqualTo(isPost);
    }

    private static Stream<Arguments> testcaseForIsMethod() {
        return Stream.of(
            Arguments.of(
                "GET /join?id=1 HTTP/1.1\n",
                "",
                true,
                false
            ),
            Arguments.of(
                "POST /join?id=1 HTTP/1.1\n"
                    + "Host: localhost:8080\n"
                    + "Content-Length: 10\n",
                "id=3456789",
                false,
                true
            )
        );
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
