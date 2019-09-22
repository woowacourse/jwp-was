package http.controller;

import http.model.HttpMethod;
import http.model.HttpProtocols;
import http.model.HttpRequest;
import http.model.HttpUri;
import http.supoort.RequestMapping;
import http.view.ModelAndView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileResourceControllerTest {
    private Controller controller = new FileResourceController(RequestMapping.GET("/index.html"));

    @Test
    void 파일리소스_컨트롤러_처리_결과가_리소스_위치가_맞는지() {
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, new HttpUri("/index.html"),
                HttpProtocols.HTTP1, null, null);

        ModelAndView modelAndView = controller.handle(httpRequest);

        assertThat(modelAndView.getViewLocation()).isEqualTo("/index.html");
    }

    @Test
    void 파일리소스_컨트롤러_처리_결과가_리소스_위치가_맞는지2() {
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, new HttpUri("/abc/def/test.test"),
                HttpProtocols.HTTP1, null, null);

        ModelAndView modelAndView = controller.handle(httpRequest);

        assertThat(modelAndView.getViewLocation()).isEqualTo("/abc/def/test.test");
    }
}