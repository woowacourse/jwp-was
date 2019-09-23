package http.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.HttpProtocol;
import http.excption.NotSupportedHttpProtocolException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpProtocolTest {

    @DisplayName("String 으로 HttpProtocol enum 을 생성한다.")
    @Test
    void successHttpProtocolOfMethod() {
        Assertions.assertDoesNotThrow(() -> HttpProtocol.of("HTTP/1.1"));
    }

    @DisplayName("지원하지 않는 HttpProtocol enum 생성은 실패한다.")
    @Test
    void failHttpProtocolOfMethod() {
        assertThrows(NotSupportedHttpProtocolException.class, () -> HttpProtocol.of("HTTP/3"));
    }
}