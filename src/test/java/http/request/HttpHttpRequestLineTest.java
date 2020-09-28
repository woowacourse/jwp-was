package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpHttpRequestLineTest {

    @Test
    void getUri() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET", "/index.html", "HTTP/1.1");

        assertThat(httpRequestLine.getUri()).isEqualTo("/index.html");
    }
}