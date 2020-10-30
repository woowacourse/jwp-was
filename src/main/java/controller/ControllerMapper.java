package controller;

import application.controller.UserController;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class ControllerMapper {

    private Map<String, Controller> controllersByUriPath = new HashMap<>();
    private Controller staticFileController = new StaticFileController();

    public ControllerMapper() {
        /* Todo: 이부분을 application 패키지에서 할 수 있으면 좋을것같음 */
        controllersByUriPath.put(UserController.CREATE_URI_PATH, new UserController());
        controllersByUriPath.put(UserController.LOGIN_URI_PATH, new UserController());
        controllersByUriPath.put(UserController.FIND_USER_URI_PATH, new UserController());
    }

    public Controller findController(HttpRequest httpRequest) {
        String uriPath = httpRequest.getUriPath();

        if (controllersByUriPath.containsKey(uriPath)) {
            return controllersByUriPath.get(uriPath);
        }
        return staticFileController;
    }
}
