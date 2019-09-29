package http.servlet.controller;

import http.request.HttpRequest;
import http.servlet.controller.Controller;
import http.servlet.controller.ControllerFinder;
import http.servlet.controller.UserCreateController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testhelper.Common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerFinderTest {
    private static ControllerFinder controllerFinder;
    private static Map<String, Controller> api = new HashMap<>();

    static {
        api.put("/user/create", new UserCreateController());
        controllerFinder = new ControllerFinder(api);
    }

    @Test
    @DisplayName("/user/create에 해당하는 컨트롤러를 반환한다")
    public void getControllerWhenPostUserCreate() throws IOException {
        HttpRequest httpRequest = Common.getHttpRequest("HTTP_POST_USER_CREATE.txt");
        assertThat(controllerFinder.find(httpRequest)).isEqualTo(api.get("/user/create"));
    }
}