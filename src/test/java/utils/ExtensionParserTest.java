package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExtensionParserTest {
    @Test
    void parseHtml() {
        assertThat(ExtensionParser.parse("/user/test.html")).isEqualTo("html");
    }

    @Test
    void parseCss() {
        assertThat(ExtensionParser.parse("/user/test.css")).isEqualTo("css");
    }
}