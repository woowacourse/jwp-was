package controller;

import exception.NotFoundUrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerHandlerTest {
    private ControllerHandler controllerHandler = new ControllerHandler();

    @Test
    @DisplayName("url에 알맞은 컨트롤러 리턴")
    void controller_return() {
        assertThat(controllerHandler.getController("/user/create").getClass()).isEqualTo(CreateUserController.class);
    }

    @Test
    @DisplayName("없는 url이 입력됐을 때")
    void controller_return_fail() {
        assertThrows(NotFoundUrlException.class, () -> {
            controllerHandler.getController("/hihi");
        });
    }
}