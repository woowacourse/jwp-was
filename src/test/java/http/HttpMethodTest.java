package http;

import http.exception.NotFoundMethodException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpMethodTest {
    @Test
    void GET_of_정상_동작() {
        assertThat(HttpMethod.of("GET")).isEqualTo(HttpMethod.GET);
    }

    @Test
    void POST_of_정상_동작() {
        assertThat(HttpMethod.of("POST")).isEqualTo(HttpMethod.POST);
    }

    @Test
    void of_없는_메소드일경우_에러() {
        assertThrows(NotFoundMethodException.class, () -> HttpMethod.of("NOT"));
    }
}