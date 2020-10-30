package web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.http.HttpMethod;
import web.http.HttpRequestLine;

class HttpRequestLineTest {

    @DisplayName("RequestLine 생성")
    @Test
    public void create() {
        String input = "GET /index.html HTTP/1.1";

        HttpRequestLine line = HttpRequestLine.from(input);

        assertThat(line.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(line.getPath()).isEqualTo("/index.html");
        assertThat(line.getVersion()).isEqualTo("HTTP/1.1");
    }
}