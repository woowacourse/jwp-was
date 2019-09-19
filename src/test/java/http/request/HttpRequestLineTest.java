package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @Test
    void RequestLine() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET http://localhost:8080 HTTP/1.1");

        assertThat(httpRequestLine.toString()).isEqualTo("GET http://localhost:8080 HTTP/1.1\r\n");
    }
}