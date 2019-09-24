package webserver;

import controller.Controller;
import controller.UserController;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import utils.FileIoUtils;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ResourceMapper {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
    }

    public static HttpResponseEntity map(HttpRequest httpRequest) throws URISyntaxException {
        String path = httpRequest.getUri().getPath();
        Controller controller = controllers.get(path);
        if (controller != null) {
            return controller.handle(httpRequest);
        }
        return findResource(STATIC_PATH + path, TEMPLATES_PATH + path);
    }

    private static HttpResponseEntity findResource(String... paths) throws URISyntaxException {
        for (String path: paths) {
            if (FileIoUtils.existFileInClasspath(path)) {
                return HttpResponseEntity.get200Response(path);
            }
        }
        return HttpResponseEntity.get404Response();
    }
}