package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import http.excption.NotFoundHeaderException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestHeaderTest {

    private HttpRequestHeader httpRequestHeader;

    @BeforeEach
    void setUp() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");

        httpRequestHeader = new HttpRequestHeader(headers);
    }

    @DisplayName("성공적으로 HttpRequestHeader 를 생성한다.")
    @Test
    void constructHttpRequestHeader() {
        assertEquals(httpRequestHeader.get("Host"), "localhost:8080");
        assertEquals(httpRequestHeader.get("Connection"), "keep-alive");
        assertEquals(httpRequestHeader.get("Accept"), "*/*");
    }

    @DisplayName("존재하지 않는 header 값을 찾을 경우 에러가 발생한다.")
    @Test
    void name() {
        assertThrows(NotFoundHeaderException.class, () -> httpRequestHeader.get("HELLO-MOTO"));
    }
}