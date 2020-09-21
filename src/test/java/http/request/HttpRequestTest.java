package http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {
    @Test
    @DisplayName("HttpRequest 생성 테스트")
    void from() throws IOException {
        String message = "GET /index.html HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        assertThat(HttpRequest.from(in)).isInstanceOf(HttpRequest.class);
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
