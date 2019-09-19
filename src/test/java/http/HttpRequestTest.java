package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws IOException {
        String plainTextRequest = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        InputStream is = new ByteArrayInputStream(plainTextRequest.getBytes());

        httpRequest = new HttpRequest(is);
    }

    @Test
    void parsePathTest() {
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    void parseMethodTest() {
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
    }

    @Test
    void parseHttpVersionTest() {
        assertThat(httpRequest.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseHeadersTest() {
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    void parseQueryParamsTest() throws IOException {
        String plainTextRequest = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        InputStream is = new ByteArrayInputStream(plainTextRequest.getBytes());

        HttpRequest httpRequest = new HttpRequest(is);

        assertThat(httpRequest.getParam("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParam("password")).isEqualTo("password");
        assertThat(httpRequest.getParam("email")).isEqualTo("javajigi@slipp.net");
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

        HttpRequest httpRequest = new HttpRequest(is);
        assertThat(httpRequest.getBody("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getBody("password")).isEqualTo("password");
        assertThat(httpRequest.getBody("email")).isEqualTo("javajigi@slipp.net");
    }
}