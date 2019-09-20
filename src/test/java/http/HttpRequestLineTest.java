package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestLineTest {

    private HttpRequestLine httpRequestLine;

    @BeforeEach
    void setUp() {
        httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");
    }

    @DisplayName("HttpRequestLine 의 요청 메서드가 일치하는지 확인한다.")
    @Test
    void getMethodFromHttpRequestLine() {
        assertEquals(httpRequestLine.getMethod(), "GET");
    }

    @DisplayName("HttpRequestLine 의 Url 이 일치하는지 확인한다.")
    @Test
    void getUrlFromHttpRequestLine() {
        assertEquals(httpRequestLine.getUrl(), "/index.html");
    }

    @DisplayName("HttpRequestLine 의 요청 메서드가 일치하는지 확인한다.")
    @Test
    void getProtocolFromHttpRequestLine() {
        assertEquals(httpRequestLine.getProtocol(), "HTTP/1.1");
    }

    @DisplayName("적절하지 않은 요청은 에러가 발생한다.")
    @Test
    void invalidRequestExceptionThrow() {
        assertThrows(InvalidRequestException.class,
                () -> new HttpRequestLine("GET FROM /index.html HTTP Protocol 1.1"));
    }
}
