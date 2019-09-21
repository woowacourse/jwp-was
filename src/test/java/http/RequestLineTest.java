package http;

import http.common.HttpVersion;
import http.request.HttpMethod;
import http.request.HttpUriParser;
import http.request.RequestLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestLineTest {

    @Test
    void create_request_line() {
        String method = "GET";
        String uri = "/index.html";
        String version = "HTTP/1.1";

        assertDoesNotThrow(() -> new RequestLine(HttpMethod.valueOf(method), HttpUriParser.parse(uri), HttpVersion.of(version)));
    }

}