package http.supoort;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingTest {
    private ServletRequest request;

    @BeforeEach
    void setUp() {
        request = ServletRequest.builder()
                .method(HttpMethod.GET)
                .uri("/index.html")
                .protocol("HTTP/1.1")
                .build();
    }

    @Test
    void 일치() {
        assertThat(RequestMapping.GET("/index.html").match(request)).isTrue();
    }

    @Test
    void 불일치() {
        assertThat(RequestMapping.GET("/index").match(request)).isFalse();
    }
}