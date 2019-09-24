package http.request;

import http.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @Test
    void RequestLine() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET http://localhost:8080 HTTP/1.1");

        assertThat(httpRequestLine.toString()).isEqualTo("GET http://localhost:8080 HTTP/1.1\r\n");
    }

    @Test
    void getMethod() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET http://localhost:8080 HTTP/1.1");

        assertThat(httpRequestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getUri() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET http://localhost:8080 HTTP/1.1");

        assertThat(httpRequestLine.getPath()).isEqualTo("http://localhost:8080");
    }
}