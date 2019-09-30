package webserver;

import controller.Controller;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static http.response.HttpStatus.METHOD_NOT_ALLOWED;

public class ControllerMapper {
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
    }

    public static void map(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        Controller controller = controllers.get(path);

        if (controller == null) {
            response.setStatus(METHOD_NOT_ALLOWED);
            return;
        }
        controller.handle(request, response);
    }
}
