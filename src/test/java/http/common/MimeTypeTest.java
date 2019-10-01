package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypeTest {
    @Test
    public void findByPathTest() {
        assertThat(MimeType.findBySufFix("index.html")).isEqualTo(MimeType.html);
        assertThat(MimeType.findBySufFix("style.css")).isEqualTo(MimeType.css);
    }
}