package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExtensionParserTest {
    @Test
    void html_파싱() {
        assertThat(ExtensionParser.parse("/user/test.html")).isEqualTo("html");
    }

    @Test
    void css_파싱() {
        assertThat(ExtensionParser.parse("/user/test.css")).isEqualTo("css");
    }
}