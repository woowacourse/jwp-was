package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exceptions.InvalidHttpRequestException;
import http.AbstractHttpRequestGenerator;
import utils.TestFileResourceLoader;

class HttpRequestParserTest extends AbstractHttpRequestGenerator {

    @DisplayName("GET 요청 파싱")
    @Test
    void parse_GET_Request() throws IOException {
        HttpRequest expected = createHttpGetRequest("GET_IndexHTML");

        InputStream in = TestFileResourceLoader.fetchTestFile("GET_IndexHTML");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.getRequestLine().getMethod()).isEqualTo(
                expected.getRequestLine().getMethod()),
            () -> assertThat(parsedRequest.getRequestLine().getPath()).isEqualTo(expected.getRequestLine().getPath())
        );
    }

    @DisplayName("GET 파라미터 요청 파싱")
    @Test
    void parse_GET_With_Parameters_Request() throws IOException {
        HttpRequest expected = createHttpGetRequest("GET_IndexHTML_parameters");

        InputStream in = TestFileResourceLoader.fetchTestFile("GET_IndexHTML_parameters");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.getRequestLine().getMethod()).isEqualTo(
                expected.getRequestLine().getMethod()),
            () -> assertThat(parsedRequest.getRequestLine().getPath()).isEqualTo(expected.getRequestLine().getPath()),
            () -> assertThat(parsedRequest.getRequestLine().getParameters("userId")).isEqualTo("sony"),
            () -> assertThat(parsedRequest.getRequestLine().getParameters("password")).isEqualTo("pw123"),
            () -> assertThat(parsedRequest.getRequestLine().getParameters("name")).isEqualTo("sony"),
            () -> assertThat(parsedRequest.getRequestLine().getParameters("email")).isEqualTo("sonypark0204@gmail.com")
        );

    }

    @DisplayName("POST 요청 파싱")
    @Test
    void parse_POST_Request() throws IOException {
        HttpRequest expected = createHttpPostRequest("POST_UserCreateRequest");

        InputStream in = TestFileResourceLoader.fetchTestFile("POST_UserCreateRequest");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.getRequestLine().getMethod()).isEqualTo(
                expected.getRequestLine().getMethod()),
            () -> assertThat(parsedRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded"),
            () -> assertThat(parsedRequest.getRequestLine().getPath()).isEqualTo(expected.getRequestLine().getPath()),
            () -> assertThat(parsedRequest.getBody().getBody()).isEqualTo(expected.getBody().getBody())
        );

    }

    @DisplayName("POST 요청 파싱 - 쿼리 스트링 포함")
    @Test
    void parse_POST_Request_With_QueryString() throws IOException {
        HttpRequest expected = createHttpPostRequest("POST_UserCreateRequestWithQueryString");

        InputStream in = TestFileResourceLoader.fetchTestFile("POST_UserCreateRequestWithQueryString");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.getRequestLine().getMethod()).isEqualTo(
                expected.getRequestLine().getMethod()),
            () -> assertThat(parsedRequest.getRequestLine().getPath()).isEqualTo(expected.getRequestLine().getPath()),
            () -> assertThat(parsedRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded"),
            () -> assertThat(parsedRequest.getBody().getBody()).isEqualTo(expected.getBody().getBody()),
            () -> assertThat(parsedRequest.getParameter("id")).isEqualTo("1"),
            () -> assertThat(parsedRequest.getParameter("userId")).isEqualTo("sonypark"),
            () -> assertThat(parsedRequest.getParameter("name")).isEqualTo("sony")
        );

    }

    @DisplayName("유효하지 않은 HTTTP 메서드 요청 파싱 - 예외 발생")
    @Test
    void parse_INVALID_HTTP_METHOD_Request() throws FileNotFoundException {
        InputStream inputStream = TestFileResourceLoader.fetchTestFile("POST_Invalid_UserCreateRequest");

        assertThatThrownBy(() -> HttpRequestParser.parse(inputStream))
            .isInstanceOf(InvalidHttpRequestException.class)
            .hasMessage("유효한 HTTP 요청이 아닙니다.");
    }

    @DisplayName("쿠키 헤더 포함 GET 요청")
    @Test
    void parse_GET_Request_With_Cookie() throws IOException {
        InputStream in = TestFileResourceLoader.fetchTestFile("GET_UserList_WithCookie");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.hasCookie()).isTrue(),
            () -> assertThat(parsedRequest.getCookies().size()).isEqualTo(1),
            () -> assertThat(parsedRequest.getCookie("logined")).isEqualTo("true")
        );
    }

    @DisplayName("쿠키 헤더 포함 POST 요청")
    @Test
    void parse_POST_Request_With_Cookie() throws IOException {
        InputStream in = TestFileResourceLoader.fetchTestFile("POST_UserCreateRequest_WithCookie");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.hasCookie()).isTrue(),
            () -> assertThat(parsedRequest.getCookies().size()).isEqualTo(1),
            () -> assertThat(parsedRequest.getCookie("logined")).isEqualTo("true")
        );
    }

    @DisplayName("쿠키 헤더 여러게 포함 POST 요청")
    @Test
    void parse_POST_Request_With_Cookies() throws IOException {
        InputStream in = TestFileResourceLoader.fetchTestFile("POST_UserCreateRequest_WithCookies");
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertAll(
            () -> assertThat(parsedRequest.hasCookie()).isTrue(),
            () -> assertThat(parsedRequest.getCookies().size()).isEqualTo(3),
            () -> assertThat(parsedRequest.getCookie("logined")).isEqualTo("true"),
            () -> assertThat(parsedRequest.getCookie("admin")).isEqualTo("false"),
            () -> assertThat(parsedRequest.getCookie("sample")).isEqualTo("123")
        );
    }
}
