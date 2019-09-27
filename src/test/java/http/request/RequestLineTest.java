package http.request;

import http.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    RequestLine requestLine;

    @BeforeEach
    void setUp() {
        requestLine = new RequestLine("GET /index.html?name=hello HTTP/1.1");
    }

    @Test
    void 메서드_확인() {
        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
    }

    @Test
    void path_확인() {
        assertThat(requestLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void version_확인() {
        assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void 쿼리_확인() {
        assertThat(requestLine.getQuery()).isEqualTo("name=hello");
    }

}