package utils;

import org.junit.jupiter.api.Test;

import static http.HttpMediaType.CSS;
import static http.HttpMediaType.HTML;
import static org.assertj.core.api.Assertions.assertThat;

class MediaTypeParserTest {
    @Test
    void html_파싱() {
        assertThat(MediaTypeParser.parse("/user/test.html")).isEqualTo(HTML);
    }

    @Test
    void css_파싱() {
        assertThat(MediaTypeParser.parse("/user/test.css")).isEqualTo(CSS);
    }
}