package http.request;

import http.exception.HttpMethodMismatchException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {
    @Test
    void httpMethod에_Null이_들어온_경우() {
        assertThatThrownBy(() -> HttpMethod.resolve(null)).isInstanceOf(HttpMethodMismatchException.class);
    }

    @Test
    void 해당_메서드가_없는_경우() {
        assertThatThrownBy(() -> HttpMethod.resolve("mismatch")).isInstanceOf(HttpMethodMismatchException.class);
    }

    @Test
    void HTTP_GET_메서드_확인() {
        assertThat(HttpMethod.resolve("GET")).isEqualTo(HttpMethod.GET);
    }

    @Test
    void HTTP_POST_메서드_확인() {
        assertThat(HttpMethod.resolve("POST")).isEqualTo(HttpMethod.POST);
    }

    @Test
    void HTTP_PUT_메서드_확인() {
        assertThat(HttpMethod.resolve("PUT")).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void HTTP_DELETE_메서드_확인() {
        assertThat(HttpMethod.resolve("DELETE")).isEqualTo(HttpMethod.DELETE);
    }
}