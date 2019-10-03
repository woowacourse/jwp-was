package http.common;

import http.parser.QueryParamsParser;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsParserTest {

    @Test
    void parse_test() {
        String queryString = "name=conas&email=conatuseus@gmail.com";
        Map<String, String> queryParams = QueryParamsParser.parse(queryString);

        assertThat(queryParams.get("name")).isEqualTo("conas");
        assertThat(queryParams.get("email")).isEqualTo("conatuseus@gmail.com");
    }

}