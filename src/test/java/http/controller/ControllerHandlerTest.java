package http.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerHandlerTest {
    @Test
    public void findByPathTest() {
        Controller controller = ControllerHandler.findByPath("test.html");
        assertThat(controller).isExactlyInstanceOf(StaticFileController.class);

        controller = ControllerHandler.findByPath("/user/create");
        assertThat(controller).isExactlyInstanceOf(CreateUserController.class);
    }
}