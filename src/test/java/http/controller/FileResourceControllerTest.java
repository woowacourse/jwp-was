package http.controller;

import http.model.HttpRequest;
import http.model.HttpStatus;
import http.supoort.HttpRequestParser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class FileResourceControllerTest {
    @Test
    void index_html() {
        String request = "GET /index.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));
        Controller controller = new FileResourceController();

        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void css_style_css() {
        String request = "GET /css/styles.css HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));
        Controller controller = new FileResourceController();

        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

}