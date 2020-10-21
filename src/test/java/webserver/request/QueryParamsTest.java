package webserver.request;

import static org.assertj.core.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.ServletFixture;

class QueryParamsTest {

    @DisplayName("QueryParams를 정상적으로 parsing하여 생성한다.")
    @Test
    void of() {
        QueryParams queryParams = QueryParams.of(ServletFixture.PATH_WITH_PARAMS);
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "JaeSung");

        assertThat(queryParams)
            .extracting(QueryParams::getQueryParams)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}