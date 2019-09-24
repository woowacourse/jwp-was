package dev.luffy.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.luffy.http.excption.NotSupportedHttpProtocolException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpProtocolTest {

    @DisplayName("지원하는 HttpProtocol 에 대하여 정상적으로 enum 객체를 반환합니다.")
    @Test
    void successStaticOfMethod() {
        assertEquals(HttpProtocol.of("HTTP/1.0"), HttpProtocol.HTTP_1_0);
        assertEquals(HttpProtocol.of("HTTP/1.1"), HttpProtocol.HTTP_1_1);
        assertEquals(HttpProtocol.of("HTTP/2"), HttpProtocol.HTTP_2);
    }

    @DisplayName("지원하지 않는 HttpProtocol 은 Exception 을 throw 합니다.")
    @Test
    void failStaticOfMethod() {
        assertThrows(NotSupportedHttpProtocolException.class, () -> HttpProtocol.of("HTTP/3"));
    }
}