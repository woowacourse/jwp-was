package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryParamsTest {
    @Test
    @DisplayName("QueryParams 하나의 키-값 파싱 테스트")
    void parse() {
        QueryParams queryParams = QueryParams.parse("red=255");
        assertThat(queryParams.get("red")).isEqualTo("255");
    }

    @Test
    @DisplayName("QueryParams 여러 개의 키-값 파싱 테스트")
    void parse_multiple() {
        QueryParams queryParams = QueryParams.parse("red=255&green=100&blue=50");
        assertThat(queryParams.get("red")).isEqualTo("255");
        assertThat(queryParams.get("green")).isEqualTo("100");
        assertThat(queryParams.get("blue")).isEqualTo("50");
    }
}
