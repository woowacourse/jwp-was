package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryParamsTest {
    @Test
    @DisplayName("QueryParams 하나의 키-값 파싱 테스트")
    void parse() {
        QueryParams queryParams = QueryParams.parse("red=255");
        assertThat(queryParams.getFirst("red")).isEqualTo("255");
    }

    @Test
    @DisplayName("QueryParams 여러 개의 키-값 파싱 테스트")
    void parse_multiple() {
        QueryParams queryParams = QueryParams.parse("red=255&green=100&blue=50");
        assertThat(queryParams.getFirst("red")).isEqualTo("255");
        assertThat(queryParams.getFirst("green")).isEqualTo("100");
        assertThat(queryParams.getFirst("blue")).isEqualTo("50");
    }

    @Test
    @DisplayName("QueryParams 중복 키 파싱 테스트")
    void parse_duplicate_key() {
        QueryParams queryParams = QueryParams.parse("friend=aaa&friend=bbb&friend=ccc");
        assertThat(queryParams.getFirst("friend")).isEqualTo("aaa");
        assertThat(queryParams.get("friend")).contains("aaa", "bbb", "ccc");
    }

    @Test
    @DisplayName("QueryParams 값이 없는 경우 파싱 테스트")
    void parse_none_value() {
        QueryParams queryParams = QueryParams.parse("none=");
        assertThat(queryParams.getFirst("none")).isEmpty();
    }
}
