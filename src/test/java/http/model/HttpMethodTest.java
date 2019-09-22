package http.model;

import http.exceptions.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

class HttpMethodTest {
    @Test
    void HTTP_메서드가_아님() {
        assertThatThrownBy(() -> HttpMethod.of("something")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void HTTP_GET_메서드() {
        assertThat(HttpMethod.of("GET")).isEqualTo(HttpMethod.GET);
    }
}