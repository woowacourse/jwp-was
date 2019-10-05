package controller.custom;

import controller.BaseControllerTest;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeControllerTest extends BaseControllerTest {
    @Test
    void index_페이지() {
        HttpRequest httpRequest = getDefaultHttpRequest("src/test/java/data/HttpRequest.txt");
        HttpResponse httpResponse = HttpResponse.of(httpRequest.getHttpVersion());

        ModelAndView modelAndView = mapAndHandle(httpRequest, httpResponse);

        assertThat(modelAndView.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(modelAndView.getView()).isEqualTo("./templates/index.html");
    }
}
