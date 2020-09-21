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
        String request = "GET /index.html HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(request.getBytes());
        assertThat(HttpRequest.from(in)).isInstanceOf(HttpRequest.class);
    }
}
