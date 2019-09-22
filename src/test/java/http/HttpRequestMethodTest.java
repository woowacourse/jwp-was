package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.excption.NotSupportedHttpRequestMethodException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestMethodTest {

    @DisplayName("웹 서버에서 지원하는 HttpRequestMethod 는 객체로 생성이 가능하다.")
    @Test
    void successHttpRequestMethodOfMethod() {
        assertDoesNotThrow(() -> HttpRequestMethod.of("GET"));
        assertDoesNotThrow(() -> HttpRequestMethod.of("POST"));
    }

    @DisplayName("웹 서버에서 지원하지 않는 HttpRequestMethod 는 에러가 발생한다.")
    @Test
    void name() {
        assertThrows(NotSupportedHttpRequestMethodException.class, () -> HttpRequestMethod.of("PUT"));
        assertThrows(NotSupportedHttpRequestMethodException.class, () -> HttpRequestMethod.of("DELETE"));
    }
}