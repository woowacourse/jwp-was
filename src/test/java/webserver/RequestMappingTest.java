package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.IndexController;
import controller.ResourceController;
import controller.UserCreateController;

class RequestMappingTest {

    @DisplayName("/가 들어왔을때 indexController 반환하는지 확인하는 테스트")
    @Test
    void getControllerByRoot() {
        Controller controller = RequestMapping.getController("/");

        assertThat(controller).isInstanceOf(IndexController.class);
    }

    @DisplayName("resource path가 들어왔을때 ResourceController 반환하는지 확인하는 테스트")
    @Test
    void getControllerByResource() {
        Controller controller = RequestMapping.getController("/index.html");

        assertThat(controller).isInstanceOf(ResourceController.class);
    }

    @DisplayName("/user/create가 들어왔을때 userCreateController 반환하는지 확인하는 테스트")
    @Test
    void getControllerByR() {
        Controller controller = RequestMapping.getController("/users");

        assertThat(controller).isInstanceOf(UserCreateController.class);
    }
}
