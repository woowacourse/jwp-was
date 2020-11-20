package controller;

import application.controller.UserController;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class ControllerMapper {

    private Map<String, Class> controllersByUriPath = new HashMap<>();
    private Class staticFileController = StaticFileController.class;

    public ControllerMapper() {
        /* Todo: 이부분을 application 패키지에서 할 수 있으면 좋을것같음 */
        controllersByUriPath.put(UserController.CREATE_URI_PATH, UserController.class);
        controllersByUriPath.put(UserController.LOGIN_URI_PATH, UserController.class);
        controllersByUriPath.put(UserController.FIND_USER_URI_PATH, UserController.class);
    }

    public Class findController(HttpRequest httpRequest) {
        String uriPath = httpRequest.getUriPath();

        if (controllersByUriPath.containsKey(uriPath)) {
            return controllersByUriPath.get(uriPath);
        }
        return staticFileController;
    }
}
