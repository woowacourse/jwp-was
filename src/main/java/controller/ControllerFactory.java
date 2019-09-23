package controller;

import http.request.Request;

import java.util.Arrays;
import java.util.List;

public class ControllerFactory {

    private List<Controller> controllers = Arrays.asList(new UserController(), new FileController());

    public Controller createController(Request request) {
        return controllers.stream()
                .filter(controller -> controller.isMapping(request.createControllerMapper()))
                .findAny()
                .orElse(new ExceptionController());
    }
}

