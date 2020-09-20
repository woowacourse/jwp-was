package webserver.utils;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValueExtractorTest {
    @DisplayName("파라미터 형식의 키, 값을 분리한다.")
    @Test
    void extract() {
        Map<String, List<String>> extract = ValueExtractor.extract("a=1&b=2&a=1");

        assertThat(extract.get("a")).hasSize(2);
        assertThat(extract.get("b")).contains("2");
    }
}