package webserver;

import exception.UnregisteredURLException;
import webserver.controller.AbstractController;
import webserver.controller.LoginController;
import webserver.controller.UserController;
import webserver.controller.UserListController;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Map<String, AbstractController> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", UserController.getInstance());
        controllers.put("/user/login", LoginController.getInstance());
        controllers.put("/user/list", UserListController.getInstance());
    }

    public static void route(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        String extension = httpRequest.getMimeType().getExtension();

        if(!extension.equals("")) {
            httpResponse.setResponseBody(httpRequest);
            return;
        }

        if (controllers.containsKey(path)) {
            controllers.get(path).service(httpRequest, httpResponse);
            return;
        }

        throw new UnregisteredURLException();
    }
}
