package http;

import http.exception.HttpVersionMismatchException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpVersionTest {
    @Test
    void 해당_버전이_없는_경우() {
        assertThatThrownBy(() -> HttpVersion.resolve("mismatch"))
                .isInstanceOf(HttpVersionMismatchException.class);
    }

    @Test
    void HTTP_1_0_버전_확인() {
        assertThat(HttpVersion.resolve("HTTP/1.0")).isEqualTo(HttpVersion.HTTP_1_0);
    }

    @Test
    void HTTP_1_1_버전_확인() {
        assertThat(HttpVersion.resolve("HTTP/1.1")).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    void HTTP_2_0_버전_확인() {
        assertThat(HttpVersion.resolve("HTTP/2.0")).isEqualTo(HttpVersion.HTTP_2_0);
    }
}