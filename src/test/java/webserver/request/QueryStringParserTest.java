package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.requestline.QueryParams;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringParserTest {

    @Test
    void parseQueryParams() {
        String queryString = "name=conas&password=1234";

        QueryParams queryParams = QueryStringParser.parseQueryParams(queryString);
        assertThat(queryParams.findParam("name")).isEqualTo("conas");
        assertThat(queryParams.findParam("password")).isEqualTo("1234");
    }
}