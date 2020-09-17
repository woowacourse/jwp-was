package controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

class ControllerMapperTest {

    @Test
    @DisplayName("/user/create 요청시 UserController 반환")
    void findUserController() {
        ControllerMapper controllerMapper = new ControllerMapper();

        String httpRequestFormat = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n";
        HttpRequest httpRequest = new HttpRequest(httpRequestFormat, "");

        assertThat(controllerMapper.findController(httpRequest))
            .isInstanceOf(UserController.class);
    }

    @Test
    @DisplayName("요청 path가 컨트롤러와 매핑되어있지 않을 때 StaticFileController 반환")
    void findStaticFileController() {
        ControllerMapper controllerMapper = new ControllerMapper();

        String httpRequestFormat = "GET /user HTTP/1.1\n"
            + "Host: localhost:8080\n";
        HttpRequest httpRequest = new HttpRequest(httpRequestFormat, "");

        assertThat(controllerMapper.findController(httpRequest))
            .isInstanceOf(StaticFileController.class);
    }
}