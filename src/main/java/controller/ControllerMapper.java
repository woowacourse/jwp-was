package controller;

import http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    public static final String USER_CONTROLLER_PATH = "/user";
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put(USER_CONTROLLER_PATH, new UserController());
    }

    public static Controller map(HttpRequest httpRequest) {
        return controllers.get(httpRequest.getPath());
    }

    public static boolean isApi(HttpRequest httpRequest) {
        return controllers.containsKey(httpRequest.getPath());
    }
}
