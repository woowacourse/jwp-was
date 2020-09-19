package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exceptions.InvalidHttpRequestException;

class HttpRequestParserTest {

    @DisplayName("GET 요청 파싱")
    @Test
    void parse_GET_Request() throws IOException {
        String request = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n"
            + "\n";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        HttpRequest expected = new HttpRequest(requestLine, requestHeader, null);

        InputStream in = new ByteArrayInputStream(request.getBytes());
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
        String request = "GET /user/create?userId=sony&password=pw123&name=sony&email=sonypark0204@gmail.com HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n"
            + "\n";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        HttpRequest expected = new HttpRequest(requestLine, requestHeader, null);

        InputStream in = new ByteArrayInputStream(request.getBytes());
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
        String request = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 71\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=sonypark&password=sony123&name=sony&email=sonypark0204@gmail.com";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());
        HttpRequest expected = new HttpRequest(requestLine, requestHeader, requestBody);

        InputStream in = new ByteArrayInputStream(request.getBytes());
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
    void parse_INVALID_HTTP_METHOD_Request() {
        String request = "INVALID_METHOD /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 71\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=sonypark&password=sony123&name=sony&email=sonypark0204@gmail.com";

        InputStream in = new ByteArrayInputStream(request.getBytes());

        assertThatThrownBy(() -> HttpRequestParser.parse(in))
            .isInstanceOf(InvalidHttpRequestException.class)
            .hasMessage("유효한 HTTP 요청이 아닙니다.");
    }
}
