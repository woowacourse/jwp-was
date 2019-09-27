package was.controller;

import org.junit.jupiter.api.Test;
import was.controller.common.ControllerTemplate;
import webserver.http.request.HttpMethod;

class MainControllerTest extends ControllerTemplate {

    @Test
    void 메인_페이지_이동() {
        init();
        movePage("/", "쿠키당");

        Controller controller = MainController.getInstance();
        controller.service(httpRequest, httpResponse);
    }

    @Test
    void 지원하지_않는_메서드() {
        allowedNotMethod(HttpMethod.POST, MainController.getInstance());
    }
}