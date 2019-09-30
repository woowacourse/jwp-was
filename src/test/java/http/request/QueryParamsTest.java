package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {
    @Test
    void 생성() {
        QueryParams queryParams = QueryParams.of("userId=woowa&password=password&name=woo");

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }
}