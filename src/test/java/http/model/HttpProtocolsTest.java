package http.model;

import http.exceptions.IllegalHttpRequestException;
import http.model.common.HttpProtocols;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpProtocolsTest {
    @Test
    void HTTP_프로토콜이_아닌경우() {
        assertThatThrownBy(() -> HttpProtocols.of("1.1")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void HTTP2_프로토콜인_경우() {
        assertDoesNotThrow(() -> HttpProtocols.of("HTTP/2.0"));
    }
}