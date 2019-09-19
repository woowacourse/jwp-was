package utils.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeParserTest {
    @Test
    @DisplayName("헤더의 Content-Type의 매개변수를 key value로 설정한다.")
    void ContentTypeParser() {
        ContentTypeParser contentTypeParser = new ContentTypeParser();
        String inputData = "Content-Type: application/x-www-form-urlencoded; charset=utf-8";
        Map<String, String> result = contentTypeParser.toMap(inputData);

        assertThat(result.get("charset")).isEqualTo("utf-8");
    }
}