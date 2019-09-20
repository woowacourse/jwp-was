package http.request;

import http.RequestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestFirstLineTest {

    RequestFirstLine requestFirstLine;

    @BeforeEach
    void setUp() {
        requestFirstLine = new RequestFirstLine("GET /index.html?name=hello HTTP/1.1");
    }

    @Test
    void 메서드_확인() {
        assertThat(requestFirstLine.getMethod()).isEqualTo(RequestMethod.GET);
    }

    @Test
    void path_확인() {
        assertThat(requestFirstLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void version_확인() {
        assertThat(requestFirstLine.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void 쿼리_확인() {
        assertThat(requestFirstLine.getQuery()).isEqualTo("name=hello");
    }

}