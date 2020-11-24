package http;

import static org.assertj.core.api.Assertions.*;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpHeadersTest {
    @Test
    @DisplayName("HttpHeaders build 테스트")
    void build() {
        HttpHeaders headers = new HttpHeaders(new LinkedHashMap<>());
        String expected = "Content-Type: text/plain\n" + "Content-Length: 100";
        headers.set("Content-Type", "text/plain");
        headers.set("Content-Length", "100");
        assertThat(headers.build()).isEqualTo(expected);
    }

    @Test
    @DisplayName("HttpHeaders 키에 해당하는 값이 다수인 경우 테스트")
    void set() {
        HttpHeaders headers = new HttpHeaders(new LinkedHashMap<>());
        String expected = "Accept: text/html, application/xhtml+xml";
        headers.set("Accept", "text/html");
        headers.set("Accept", "application/xhtml+xml");
        assertThat(headers.build()).isEqualTo(expected);
    }
}
