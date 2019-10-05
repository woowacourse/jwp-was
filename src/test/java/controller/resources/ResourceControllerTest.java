package controller.resources;

import controller.BaseControllerTest;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceControllerTest extends BaseControllerTest {

    @Test
    void resource_controller_test() {
        String resourceRequest = "GET /css/styles.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "\n";
        HttpRequest request = getDefaultHttpRequest(resourceRequest.getBytes());
        HttpResponse response = HttpResponse.of(request.getHttpVersion());

        ModelAndView modelAndView = mapAndHandle(request, response);

        assertThat(modelAndView.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(modelAndView.getView()).isEqualTo("./static/css/styles.css");
    }

}