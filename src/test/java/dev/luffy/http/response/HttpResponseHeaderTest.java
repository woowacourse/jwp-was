package dev.luffy.http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpResponseHeaderTest {

    private HttpResponseHeader httpResponseHeader;

    @BeforeEach
    void setUp() {
        httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.addHeader("Content-Type", "text/html;charset-utf-8");
        httpResponseHeader.addHeader("Content-Length", "1000");
    }

    @DisplayName("HttpResponseHeader 의 combine 메서드가 적절한 String 을 반환한다.")
    @Test
    void combine() {
        String combinedResponseHeaders = httpResponseHeader.combine();

        assertTrue(combinedResponseHeaders.contains("Content-Type: text/html;charset-utf-8\r\n"));
        assertTrue(combinedResponseHeaders.contains("Content-Length: 1000\r\n"));
        assertEquals(
                combinedResponseHeaders.length(),
                "Content-Type: text/html;charset-utf-8\r\nContent-Length: 1000\r\n".length()
        );
    }
}
