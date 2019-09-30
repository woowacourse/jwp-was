package utils.parser;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderFieldParserTest {
    private final SimpleStringParser contentTypeParser = KeyValueParserFactory.httpHeaderFieldAttrParser();

    @Test
    void parseContentType() {
        final String CONTENT_TYPE_INPUT = "Content-Type: application/json; charset=utf-8";
        final Map<String, String> result = contentTypeParser.interpret(CONTENT_TYPE_INPUT);
        assertThat(result.get("charset")).isEqualTo("utf-8");
    }
}