package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private final String HTTP_GET_REQUEST = "GET /index.html HTTP/1.1\r\nHost: localhost:8080\r\nConnection: keep-alive\r\nCache-Control: max-age=0\r\n\r\n";
    private final String HTTP_POST_REQUEST = "POST /index.html HTTP/1.1\r\nHost: localhost:8080\r\nConnection: keep-alive\r\nCache-Control: max-age=0\r\nContent-Length: 33\r\n\r\nuserId=javajigi&password=password";

    @DisplayName("RequestHeader 객체 생성 - GET REQUEST")
    @Test
    void constructor_isGet_RequestHeader() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(HTTP_GET_REQUEST.getBytes());
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        assertThat(httpRequest.isGet()).isTrue();
        assertThat(httpRequest.getBody()).isNull();
        assertThat(httpRequest.getResourcePath()).isEqualTo("/index.html");
    }

    @DisplayName("RequestHeader 객체 생성 - POST REQUEST")
    @Test
    void constructor_isPost_RequestHeader() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(HTTP_POST_REQUEST.getBytes());
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        assertThat(httpRequest.isPost()).isTrue();
        assertThat(httpRequest.getBody()).isEqualTo("userId=javajigi&password=password");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/index.html");
    }
}
