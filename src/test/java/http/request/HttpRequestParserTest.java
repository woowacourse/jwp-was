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
            () -> assertThat(parsedRequest.getRequestLine().getPath()).isEqualTo(expected.getRequestLine().getPath()),
            () -> assertThat(parsedRequest.getRequestBody().getBody()).isEqualTo(expected.getRequestBody().getBody())
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
}
