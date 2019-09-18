package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    private Request request;

    @BeforeEach
    void setUp() throws IOException {
        String plainTextRequest = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        InputStream is = new ByteArrayInputStream(plainTextRequest.getBytes());

        request = new Request(is);
    }

    @Test
    void parsePathTest() {
        assertThat(request.getPath()).isEqualTo("/index.html");
    }

    @Test
    void parseMethodTest() {
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void parseHttpVersionTest() {
        assertThat(request.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseHeadersTest() {
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    void parseQueryParamsTest() throws IOException {
        String plainTextRequest = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        InputStream is = new ByteArrayInputStream(plainTextRequest.getBytes());

        Request request = new Request(is);

        assertThat(request.getParam("userId")).isEqualTo("javajigi");
        assertThat(request.getParam("password")).isEqualTo("password");
        assertThat(request.getParam("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void parseBodyTest() throws IOException {
        String plainTextRequest = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream is = new ByteArrayInputStream(plainTextRequest.getBytes());

        Request request = new Request(is);
        assertThat(request.getBody("userId")).isEqualTo("javajigi");
        assertThat(request.getBody("password")).isEqualTo("password");
        assertThat(request.getBody("email")).isEqualTo("javajigi@slipp.net");
    }
}