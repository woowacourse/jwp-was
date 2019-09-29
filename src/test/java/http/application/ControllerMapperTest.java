package http.application;

import http.application.controller.BasicController;
import http.application.controller.CreateUserController;
import http.application.controller.LoginController;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {

    private static final String NONE_RESISTER_URL = "";

    @Test
    void 로그인_Controller_매핑() {
        Controller controller = ControllerMapper.controllerMapping("/user/login");

        assertThat(controller.getClass()).isEqualTo(LoginController.class);
    }

    @Test
    void 유저_생성_Controller_매핑() {
        Controller controller = ControllerMapper.controllerMapping("/user/create");

        assertThat(controller.getClass()).isEqualTo(CreateUserController.class);
    }

    @Test
    void 기본_Controller_매핑() {
        Controller controller = ControllerMapper.controllerMapping(NONE_RESISTER_URL);

        assertThat(controller.getClass()).isEqualTo(BasicController.class);
    }
}