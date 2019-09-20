package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {

    @Test
    void find_GET_method() {
        assertThat(HttpMethod.valueOf("GET")).isEqualTo(HttpMethod.GET);
    }
}