package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestUriTest {
    @DisplayName("생성자 테스트")
    @Test
    void create() {
        String line = "GET /index.html HTTP/1.1";

        RequestUri requestUri = new RequestUri(line);

        assertAll(
                () -> assertThat(requestUri.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestUri.getUri()).isEqualTo("/index.html")
        );
    }
}
