package http;

import http.request.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {

    @Test
    void find_GET_method() {
        assertThat(HttpMethod.valueOf("GET")).isEqualTo(HttpMethod.GET);
    }
}