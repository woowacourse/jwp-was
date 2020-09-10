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

public class RequestHeaderTest {

    private final String HTTP_GET_REQUEST = "GET /index.html HTTP/1.1\r\nHost: localhost:8080\r\nConnection: keep-alive\r\nCache-Control: max-age=0\r\n\r\n";
    private final String HTTP_POST_REQUEST = "POST /index.html HTTP/1.1\r\nHost: localhost:8080\r\nConnection: keep-alive\r\nCache-Control: max-age=0\r\nContent-Length: 33\r\n\r\nuserId=javajigi&password=password";

    @DisplayName("RequestHeader 객체 생성 - GET REQUEST")
    @Test
    void constructor_isGet_RequestHeader() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(HTTP_GET_REQUEST.getBytes());
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestHeader requestHeader = new RequestHeader(bufferedReader);

        assertThat(requestHeader.isGet()).isTrue();
        assertThat(requestHeader.getBody()).isNull();
        assertThat(requestHeader.getFirstLine()).isEqualTo("GET /index.html HTTP/1.1");
    }

    @DisplayName("RequestHeader 객체 생성 - POST REQUEST")
    @Test
    void constructor_isPost_RequestHeader() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(HTTP_POST_REQUEST.getBytes());
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestHeader requestHeader = new RequestHeader(bufferedReader);

        assertThat(requestHeader.isPost()).isTrue();
        assertThat(requestHeader.getBody()).isEqualTo("userId=javajigi&password=password");
        assertThat(requestHeader.getFirstLine()).isEqualTo("POST /index.html HTTP/1.1");
    }
}
