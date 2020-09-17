package controller;

import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class ControllerMapper {

    /* add constant when you make a new controller */
    private static final String USER_CREATE_URI_PATH = "/user/create";

    private Map<String, Controller> controllersByUriPath = new HashMap<>();
    private Controller staticFileController = new StaticFileController();

    public ControllerMapper() {
        /* put new controller when you make a new controller */
        controllersByUriPath.put(USER_CREATE_URI_PATH, new UserController());
    }

    public Controller findController(HttpRequest httpRequest) {
        String uriPath = httpRequest.getUriPath();

        if (controllersByUriPath.containsKey(uriPath)) {
            return controllersByUriPath.get(uriPath);
        }
        return staticFileController;
    }
}
