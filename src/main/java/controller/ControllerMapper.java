package controller;

import controller.user.UserController;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class ControllerMapper {

    private Map<String, Controller> controllersByUriPath = new HashMap<>();
    private Controller staticFileController = new StaticFileController();

    public ControllerMapper() {
        /* put new controller when you make a new controller */
        controllersByUriPath.put(UserController.CREATE_URI_PATH, new UserController());
        controllersByUriPath.put(UserController.LOGIN_URI_PATH, new UserController());
    }

    public Controller findController(HttpRequest httpRequest) {
        String uriPath = httpRequest.getUriPath();

        if (controllersByUriPath.containsKey(uriPath)) {
            return controllersByUriPath.get(uriPath);
        }
        return staticFileController;
    }
}
