package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestLineTest {

    @Test
    void create_request_line() {
        String method = "GET";
        String uri = "/index.html";
        String version = "HTTP/1.1";

        assertDoesNotThrow(() -> new RequestLine(HttpMethod.valueOf(method), new HttpUri(uri), HttpVersion.of(version)));
    }

}