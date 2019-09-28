package webserver;

import controller.Controller;
import controller.UserController;
import http.request.HttpRequest;
import http.request.HttpUri;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static http.response.HttpStatus.NOT_FOUND;

public class ControllerMapper {
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
    }

    public static void map(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        HttpUri uri = httpRequest.getUri();
        String path = uri.getPath();
        Controller controller = controllers.get(path);

        if (controller == null) {
            httpResponse.setStatus(NOT_FOUND);
            httpResponse.forward("/error.html");
            return;
        }
        controller.handle(httpRequest, httpResponse);
    }
}
