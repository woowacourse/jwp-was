package utils.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleStringParserTest {
    private SimpleStringParser simpleStringParser;

    @BeforeEach
    void setUp() {
        simpleStringParser = new SimpleStringParser("/", "=");
    }

    @Test
    void interpretSingleAttribute() {
        final String INPUT = "key=value /";
        final Map<String, String> result = simpleStringParser.interpret(INPUT);
        assertThat(result.get("key")).isEqualTo("value");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void interpretMultipleAttributes() {
        final String INPUT = "one=1 /two=2/three=3";
        final Map<String, String> result = simpleStringParser.interpret(INPUT);
        assertThat(result.get("one")).isEqualTo("1");
        assertThat(result.get("two")).isEqualTo("2");
        assertThat(result.get("three")).isEqualTo("3");
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void interpretInvalidInput() {
        final String INPUT = "///////k?d//";
        final Map<String, String> result = simpleStringParser.interpret(INPUT);
        assertThat(result.isEmpty()).isTrue();
    }
}