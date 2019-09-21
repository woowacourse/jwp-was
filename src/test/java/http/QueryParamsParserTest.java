package http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QueryParamsParserTest {

    @Test
    void get_query_params() {
        String queryString="name=conas&password=1234";

        Map<String, String> params = QueryParamsParser.parse(queryString);
        assertThat(params.get("name")).isEqualTo("conas");
        assertThat(params.get("password")).isEqualTo("1234");
    }
}