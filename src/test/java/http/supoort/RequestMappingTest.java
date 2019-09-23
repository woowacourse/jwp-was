package http.supoort;

import http.model.common.HttpProtocols;
import http.model.request.HttpMethod;
import http.model.request.HttpUri;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingTest {
    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        httpRequest = new HttpRequest(HttpMethod.GET, new HttpUri("/index.html"), HttpProtocols.HTTP1, null, null);
    }

    @Test
    void 일치() {
        assertThat(RequestMapping.GET("/index.html").match(httpRequest)).isTrue();
    }

    @Test
    void 불일치() {
        assertThat(RequestMapping.GET("/index").match(httpRequest)).isFalse();
    }
}