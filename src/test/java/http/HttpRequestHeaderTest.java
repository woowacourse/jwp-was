package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestHeaderTest {

    @DisplayName("성공적으로 HttpRequestHeader 를 생성한다.")
    @Test
    void constructHttpRequestHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(headers);
        assertEquals(httpRequestHeader.get("Host"), "localhost:8080");
        assertEquals(httpRequestHeader.get("Connection"), "keep-alive");
        assertEquals(httpRequestHeader.get("Accept"), "*/*");
    }
}