package http.model;

import http.supoort.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpProtocolsTest {
    @Test
    void ENUM_메서드_확인() {
        assertDoesNotThrow(() -> HttpProtocols.of("HTTP/1.1"));
    }

    @Test
    void HTTP_프로토콜이_아닌경우() {
        assertThatThrownBy(() -> HttpProtocols.of("1.1")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void HTTP_프로토콜이_맞는경우() {
        assertDoesNotThrow(() -> HttpProtocols.of("HTTP/2.0"));
    }
}