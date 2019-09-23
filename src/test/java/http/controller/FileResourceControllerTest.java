package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileResourceControllerTest {
    private Controller controller = new FileResourceController(RequestMapping.GET("/index.html"));

    @Test
    void 파일리소스_응답이_적절한지() {
        ServletRequest request = ServletRequest.builder()
                .requestLine(HttpMethod.GET, "/index.html", "HTTP/1.1")
                .build();


        ServletResponse response = new ServletResponse(new ByteArrayOutputStream());

        assertThat(controller.canHandle(request)).isTrue();

        controller.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getUri()).isNotEmpty();
        assertThat(response.getHeader("Content-Type")).isEqualTo("text/html");
    }
}