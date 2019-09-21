package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypeTest {
    @Test
    public void findByPathTest() {
        assertThat(MimeType.findByPath("index.html")).isEqualTo(MimeType.html);
        assertThat(MimeType.findByPath("style.css")).isEqualTo(MimeType.css);
    }
}