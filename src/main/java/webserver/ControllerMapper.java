package webserver;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import controller.exception.ControllerNotFoundException;
import http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();

        controllers.put("/user/create", new UserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserController());
    }

    public static Controller map(HttpRequest request) {
        String path = request.getPath();

        if (doesNotHaveMatchedController(path)) {
            throw new ControllerNotFoundException();
        }
        return controllers.get(path);
    }

    private static boolean doesNotHaveMatchedController(String path) {
        return controllers.get(path) == null;
    }
}
