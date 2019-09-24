package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileResourceControllerTest extends BaseControllerTest {
    private Controller controller = new FileResourceController(RequestMapping.GET("/index.html"));

    @Test
    void 파일리소스_응답이_적절한지() {
        ServletRequest request = getDefaultRequest(HttpMethod.GET, "/index.html").build();
        ServletResponse response = getDefaultResponse();

        assertThat(controller.canHandle(request)).isTrue();

        controller.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getView()).isNotEmpty();
    }
}