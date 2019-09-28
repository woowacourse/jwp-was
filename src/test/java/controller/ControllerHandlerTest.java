package controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerHandlerTest {
    private ControllerHandler controllerHandler = new ControllerHandler();

    @Test
    void url에_알맞은_컨트롤러_리턴() {
        assertThat(controllerHandler.getController("/user/create").filter(controller -> controller.getClass().equals(CreateUserController.class)).isPresent());
    }

    @Test
    void 없는_url_입력() {
        assertThat(controllerHandler.getController("/jaj")).isEmpty();
    }
}