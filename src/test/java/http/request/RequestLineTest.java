package http.request;

import http.common.HttpVersion;
import http.parser.HttpUriParser;
import http.parser.RequestLineParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestLineTest {

    @Test
    void create_request_line() {
        String method = "GET";
        String uri = "/index.html";
        String version = "HTTP/1.1";

        assertDoesNotThrow(() -> new RequestLine(HttpMethod.valueOf(method), HttpUriParser.parse(uri), HttpVersion.of(version)));
    }

    @Test
    void get_class_path_templates() {
        RequestLine requestLine = RequestLineParser.parse("GET /index.html HTTP/1.1");

        assertThat(requestLine.getClassPath()).isEqualTo("./templates/index.html");
    }

    @Test
    void get_class_path_static() {
        RequestLine requestLine = RequestLineParser.parse("GET /css/styles.css HTTP/1.1");

        assertThat(requestLine.getClassPath()).isEqualTo("./static/css/styles.css");
    }

}