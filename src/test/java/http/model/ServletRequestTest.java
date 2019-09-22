package http.model;

import http.exceptions.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ServletRequestTest {

    @Test
    void 빌더_정상_동작() {
        ServletRequest servletRequest = ServletRequest.builder()
                .method("GET")
                .uri("/index.html")
                .protocol("HTTP/1.1")
                .headers(new HashMap<String, String>() {{
                    put("Cookie", "Cookie");
                }}).build();

        assertThat(servletRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(servletRequest.getProtocol()).isEqualTo(HttpProtocols.HTTP1);
        assertThat(servletRequest.getHeader("Cookie")).isEqualTo("Cookie");
    }

    @Test
    void 잘못된_메서드() {
        assertThatThrownBy(() -> ServletRequest.builder()
                .method("HEADER").build()).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 잘못된_URI() {
        assertThatThrownBy(() -> ServletRequest.builder()
                .uri("malformed")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 잘못된_프로토콜() {
        assertThatThrownBy(() -> ServletRequest.builder()
                .protocol("1.1").build()).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 헤더_없이_빌드할_경우() {
        ServletRequest request = ServletRequest.builder()
                .method("GET").build();

        assertThat(request.getHeaders()).isNotNull();
    }

    @Test
    void 파라미터_없이_빌드할_경우() {
        ServletRequest request = ServletRequest.builder()
                .method("GET").build();

        assertThat(request.getParameters()).isNotNull();
    }
}