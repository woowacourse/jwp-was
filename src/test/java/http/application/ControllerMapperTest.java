package http.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {

    private static final String NONE_RESISTER_URL = "";

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