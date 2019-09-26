package webserver;

import webserver.controller.AbstractController;
import webserver.controller.UserController;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Map<String, AbstractController> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", UserController.getInstance());
    }

    public static void route(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();

        if (controllers.containsKey(path)) {
            controllers.get(path).service(httpRequest, httpResponse);
        }
    }
}
