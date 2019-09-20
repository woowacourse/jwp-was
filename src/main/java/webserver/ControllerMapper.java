package webserver;

import controller.Controller;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import http.response.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
    }

    public static HttpResponseEntity map(HttpRequest httpRequest) {
        Controller controller = controllers.get(httpRequest.getUri().getPath());
        if (controller != null) {
            return controller.handle(httpRequest);
        }
        return new HttpResponseEntity("/error.html", HttpStatus.NOT_FOUND);
    }
}
