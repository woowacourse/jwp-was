package webserver.request.requestline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {

    private QueryParams queryParams;

    @BeforeEach
    void setUp() {
        queryParams = new QueryParams();
    }

    @Test
    void addAndFindParam() {
        String name = "name";
        String value = "nick";

        assertThat(queryParams.findParam(name)).isNull();
        queryParams.addParam(name, value);
        assertThat(queryParams.findParam(name)).isEqualTo(value);
    }

    @Test
    void isEmpty() {
        assertThat(queryParams.isEmpty()).isTrue();
    }
}