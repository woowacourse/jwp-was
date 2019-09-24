package controller;

import controller.core.Controller;
import controller.exception.PathNotFoundException;
import http.request.HttpRequest;

import java.util.Arrays;
import java.util.List;

public class ControllerFactory {
    private static final List<Controller> controllers = Arrays.asList(
            new HomeController(),
            new UserController()
    );

    public static Controller mappingController(HttpRequest httpRequest) {
        return controllers.stream()
                .filter(controller -> controller.isMapping(httpRequest))
                .findAny()
                .orElseThrow(PathNotFoundException::new);
    }
}