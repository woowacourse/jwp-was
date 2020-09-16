package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestUtilsTest {
    @Test
    void extractUrl() {
        String url = RequestUtils.extractUrl("GET /index.html HTTP/1.1");
        assertThat(url).isEqualTo("/index.html");
    }
}
