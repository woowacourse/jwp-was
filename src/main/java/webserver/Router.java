package webserver;

import exception.UnregisteredURLException;
import webserver.controller.AbstractController;
import webserver.controller.IndexController;
import webserver.controller.LoginController;
import webserver.controller.UserController;
import webserver.controller.UserListController;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Map<String, AbstractController> controllers = new HashMap<>();
    private static final UserController USER_CONTROLLER = new UserController();
    private static final IndexController INDEX_CONTROLLER = new IndexController();
    private static final UserListController USER_LIST_CONTROLLER = new UserListController();
    private static final LoginController LOGIN_CONTROLLER = new LoginController();

    private Router() {
    }

    public static Router getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final Router INSTANCE = new Router();
    }

    static {
        controllers.put("/user/create", USER_CONTROLLER);
        controllers.put("/user/login", LOGIN_CONTROLLER);
        controllers.put("/user/list", USER_LIST_CONTROLLER);
        controllers.put("/index.html", INDEX_CONTROLLER);
        controllers.put("/user/form.html", USER_CONTROLLER);
        controllers.put("/user/login.html", LOGIN_CONTROLLER);
    }

    public HttpResponse serve(HttpRequest httpRequest) throws IOException {
        String path = httpRequest.getPath();
        String extension = httpRequest.getMimeType().getExtension();

        if (extension.equals(".html") || extension.equals("")) {
            return route(httpRequest);
        }

        return HttpResponse.staticFile(httpRequest, path);
    }

    private HttpResponse route(HttpRequest httpRequest) throws IOException {
        String path = httpRequest.getPath();
        if (controllers.containsKey(path)) {
            return controllers.get(path).service(httpRequest);
        }
        throw new UnregisteredURLException();
    }
}
