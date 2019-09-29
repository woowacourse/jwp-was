package http.request;

import http.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @Test
    void RequestLine() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");

        assertThat(httpRequestLine.toString()).isEqualTo("GET /index.html HTTP/1.1\r\n");
    }

    @Test
    void getMethod() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /user/list.html HTTP/1.1");

        assertThat(httpRequestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getUri() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /user/login.html HTTP/1.1");

        assertThat(httpRequestLine.getPath()).isEqualTo("/user/login.html");
    }
}