package http.request;

import http.exception.HttpMethodMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {
    @Test
    @DisplayName("Null이 들어온 경우 HttpMethodMismatchException 발생")
    void nullMethod() {
        assertThatThrownBy(() -> HttpMethod.resolve(null)).isInstanceOf(HttpMethodMismatchException.class);
    }

    @Test
    @DisplayName("해당 메서드가 없는 경우 HttpMethodMismatchException 발생")
    void notExistMethod() {
        assertThatThrownBy(() -> HttpMethod.resolve("mismatch")).isInstanceOf(HttpMethodMismatchException.class);
    }

    @Test
    @DisplayName("GET 문자열로 HttpMethod.GET 가져오기")
    void httpGet() {
        assertThat(HttpMethod.resolve("GET")).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("POST 문자열로 HttpMethod.POST 가져오기")
    void resolveHttpPost() {
        assertThat(HttpMethod.resolve("POST")).isEqualTo(HttpMethod.POST);
    }

    @Test
    @DisplayName("PUT 문자열로 HttpMethod.PUT 가져오기")
    void resolveHttpPut() {
        assertThat(HttpMethod.resolve("PUT")).isEqualTo(HttpMethod.PUT);
    }

    @Test
    @DisplayName("DELETE 문자열로 HttpMethod.DELETE 가져오기")
    void resolveHttpDelete() {
        assertThat(HttpMethod.resolve("DELETE")).isEqualTo(HttpMethod.DELETE);
    }
}