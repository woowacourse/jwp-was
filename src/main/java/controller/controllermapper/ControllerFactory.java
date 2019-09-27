package controller.controllermapper;

import controller.*;
import exception.PathNotFoundException;
import http.request.Request;

import java.util.Arrays;
import java.util.List;

public class ControllerFactory {

    private List<Controller> controllers = Arrays.asList(new UserController(), new FileController(), new LoginController());

    public Controller mappingController(Request request) {
        try {
            return controllers.stream()
                    .filter(controller -> controller.isMapping(request.createControllerMapper()))
                    .findAny()
                    .orElseThrow(PathNotFoundException::new);
        } catch (PathNotFoundException e) {

            if ("경로를 찾을 수 없습니다.".equals(e.getMessage())) {
                return new ExceptionController();
            }

            //todo: 다른 분기 처리도 exception 적용.
            throw new PathNotFoundException();

        }
    }
}

