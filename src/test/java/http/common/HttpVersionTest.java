package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpVersionTest {

    @Test
    void 해당버전이_존재하면_true를_리턴() {
        String version = "HTTP/1.1";
        assertThat(HttpVersion.isExistVersion(version)).isTrue();
    }

    @Test
    void 해당버전이_존재하지_않으면_false를_리턴() {
        String version = "HTTP/1.2221";
        assertThat(HttpVersion.isExistVersion(version)).isFalse();
    }
}