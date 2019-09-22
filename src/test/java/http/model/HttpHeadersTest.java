package http.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpHeadersTest {
    @Test
    void 기본동작() {
        HttpHeaders headers = new HttpHeaders();
        headers.addHeader("Host", "http://localhost");
        headers.addHeader("Referer", "http://google.com");
        headers.addHeader("Cookie", "cookie");

        assertThat(headers.getHeader("Host")).isEqualTo("http://localhost");
        assertThat(headers.getHeader("Referer")).isEqualTo("http://google.com");
        assertThat(headers.getHeader("Cookie")).isEqualTo("cookie");
    }

    @Test
    void 외부에서_변경하려_할때_거부() {
        HttpHeaders headers = new HttpHeaders();

        Map<String, String> field = headers.getHeaders();
        assertThatThrownBy(() -> field.put("some", "thing")).isInstanceOf(UnsupportedOperationException.class);
    }
}