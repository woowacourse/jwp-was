package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParameterTest {

    @Test
    void oneParameter() {
        QueryParameter queryParameter = QueryParameter.of("userId=test");
        assertThat(queryParameter.getValue("userId")).isEqualTo("test");
    }

    @Test
    void getValue() {
        QueryParameter queryParameter = QueryParameter.of("userId=test&userPassword=123");
        assertThat(queryParameter.getValue("userId")).isEqualTo("test");
        assertThat(queryParameter.getValue("userPassword")).isEqualTo("123");
    }
}