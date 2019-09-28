package utils.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KeyValueParserTest {
    @Test
    @DisplayName("'/'로 줄을 나누고, '='으로 key value를 설정한다.")
    void toMap() {
        String inputData = "one=1/two=2/three=3";
        Map<String, String> result = KeyValueParser.toMap("/", "=", inputData);

        assertThat(result.get("one")).isEqualTo("1");
        assertThat(result.get("two")).isEqualTo("2");
        assertThat(result.get("three")).isEqualTo("3");
    }
}