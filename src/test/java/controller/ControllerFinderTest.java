package controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerFinderTest {

    @Test
    void findController() {
        Controller controller = ControllerFinder.findController("/index.html");
        assertThat(controller).isExactlyInstanceOf(IndexController.class);
    }

    @Test
    void findController_invalidUri() {
        Controller controller = ControllerFinder.findController("/index.css");
        assertThat(controller).isExactlyInstanceOf(ResourceController.class);
    }
}