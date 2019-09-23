package webserver.request.requestline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @Test
    void findFilePath_templates() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET", "/index.html", "HTTP/1.1");

        assertThat(httpRequestLine.findFilePath()).isEqualTo("./templates/index.html");
    }

    @Test
    void findFilePath_static() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET", "/index.css", "HTTP/1.1");

        assertThat(httpRequestLine.findFilePath()).isEqualTo("./static/index.css");
    }
}