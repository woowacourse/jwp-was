package utils.parser.simple;

import org.junit.jupiter.api.Test;
import utils.parser.simple.ContentTypeParser;
import utils.parser.simple.KeyValueParserFactory;
import utils.parser.simple.SimpleStringParser;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeParserTest {
    private final SimpleStringParser contentTypeParser = KeyValueParserFactory.contentTypeParser();

    @Test
    void parseContentType() {
        final String CONTENT_TYPE_INPUT = "Content-Type: application/json; charset=utf-8";
        final Map<String, String> result = contentTypeParser.interpret(CONTENT_TYPE_INPUT);
        assertThat(result.get("charset")).isEqualTo("utf-8");
    }
}