package http.common;

import exceptions.InvalidHttpVersionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpVersionTest {

    @Test
    void find_http_version() {
        assertThat(HttpVersion.of("HTTP/1.0")).isEqualTo(HttpVersion.HTTP_1_0);
    }

    @Test
    void none_match_http_version() {
        assertThrows(InvalidHttpVersionException.class, () -> HttpVersion.of("HTTP/0.0"));
    }
}