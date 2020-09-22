package http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.HttpMethod;

public class HttpRequestTest {
    @Test
    @DisplayName("HttpRequest 생성 테스트")
    void from() throws IOException {
        String message = "GET /users?name=홍길동 HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Accept: */*";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        assertThat(httpRequest.method()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.path()).isEqualTo("/users");
        assertThat(httpRequest.version()).isEqualTo("HTTP/1.1");
        assertThat(httpRequest.getParam("name")).isEqualTo("홍길동");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    @DisplayName("HttpRequest getBody 테스트")
    void getBody() throws IOException {
        String message = "POST /user/create HTTP/1.1\r\n" +
            "Content-Type: application/x-www-form-urlencoded\r\n" +
            "Content-Length: 10\r\n\r\n" +
            "color=blue";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        assertThat(httpRequest.getBody()).isEqualTo("color=blue");
    }
}
