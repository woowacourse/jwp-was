package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileResourceControllerTest {
    private Controller controller = new FileResourceController(RequestMapping.GET("/index.html"));

    @Test
    void 파일리소스를_잘_가져오는지() {
        ServletRequest request = ServletRequest.builder()
                .method(HttpMethod.GET)
                .uri("/index.html")
                .protocol("HTTP/1.1")
                .build();

        ServletResponse response = new ServletResponse();

        assertThat(controller.canHandle(request)).isTrue();

        controller.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getHeader("Content-Type")).isEqualTo("text/html");
    }
}