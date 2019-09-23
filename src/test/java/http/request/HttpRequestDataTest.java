package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import http.excption.NotExistKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestDataTest {

    private HttpRequestData httpRequestData;

    @BeforeEach
    void setUp() {
        Map<String, String> data = new HashMap<>();
        data.put("Host", "localhost:8080");
        data.put("Connection", "keep-alive");
        data.put("Accept", "*/*");
        httpRequestData = new HttpRequestData(data);
    }

    @DisplayName("존재하는 Key 에 대해서는 성공적으로 Value 를 get 한다.")
    @Test
    void successGet() {
        assertEquals(httpRequestData.get("Host"), "localhost:8080");
    }

    @DisplayName("존재하지 않는 Key 는 Exception 을 throw 한다.")
    @Test
    void failGet() {
        assertThrows(NotExistKeyException.class, () -> httpRequestData.get("Content-Length"));
    }
}