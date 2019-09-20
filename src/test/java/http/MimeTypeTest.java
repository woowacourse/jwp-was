package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypeTest {
    @Test
    void html_of() {
        assertThat(MimeType.of("abc.html")).isEqualTo("text/html");
    }

    @Test
    void javascript_of() {
        assertThat(MimeType.of("abc.js")).isEqualTo("application/javascript");
    }

    @Test
    void css_of() {
        assertThat(MimeType.of("abc.css")).isEqualTo("text/css");
    }

    @Test
    void Map에_없는_확장자인_경우() {
        assertThat(MimeType.of("abc.abc")).isEqualTo("text/plain");
    }
}