package controller;

import exception.PathNotFoundException;
import http.request.Request;

import java.util.Arrays;
import java.util.List;

public class ControllerFactory {

    private List<Controller> controllers = Arrays.asList(new UserController(), new FileController());

    public Controller createController(Request request) {
        try {
            return controllers.stream()
                    .filter(controller -> controller.isMapping(request.createControllerMapper()))
                    .findAny()
                    .orElseThrow(() -> new PathNotFoundException());
        } catch (PathNotFoundException e) {

            if ("경로를 찾을 수 없습니다.".equals(e.getMessage())) {
                return new ExceptionController();
            }
            throw new PathNotFoundException();

        }
    }
}

