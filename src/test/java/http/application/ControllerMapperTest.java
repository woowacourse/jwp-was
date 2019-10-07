package http.application;

import http.application.controller.BasicController;
import http.application.controller.CreateUserController;
import http.application.controller.LoginController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {

    private static final String NONE_RESISTER_URL = "";

    @Test
    @DisplayName("/user/login으로 매핑된 컨트롤러 클래스가 LoginController인지 검증")
    void 로그인_Controller_매핑() {
        Controller controller = ControllerMapper.controllerMapping("/user/login");

        assertThat(controller.getClass()).isEqualTo(LoginController.class);
    }

    @Test
    @DisplayName("/user/create으로 매핑된 컨트롤러 클래스가 CreateUserController인지 검증")
    void 유저_생성_Controller_매핑() {
        Controller controller = ControllerMapper.controllerMapping("/user/create");

        assertThat(controller.getClass()).isEqualTo(CreateUserController.class);
    }

    @Test
    @DisplayName("등록되지 않은 url인 경우 정적 파일을 전달하는 BasicController인지 검증")
    void 기본_Controller_매핑() {
        Controller controller = ControllerMapper.controllerMapping(NONE_RESISTER_URL);

        assertThat(controller.getClass()).isEqualTo(BasicController.class);
    }
}