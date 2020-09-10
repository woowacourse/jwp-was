package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RequestLineTest {

    @Test
    void getUri() {
        RequestLine requestLine = new RequestLine("GET", "/index.html", "HTTP/1.1");

        assertThat(requestLine.getUri()).isEqualTo("/index.html");
    }
}