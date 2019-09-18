package http.model;

import http.supoort.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class HttpProtocolTest {

    @Test
    void HTTP_프로토콜이_아닌경우() {
        assertThatThrownBy(()-> new HttpProtocol("1.1")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void HTTP_프로토콜이_맞는경우() {
        assertDoesNotThrow(()-> new HttpProtocol("HTTP/2.0"));
    }
}