package webserver;

import controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/", IndexController.getInstance());
        controllers.put("/user/create", UserController.getInstance());
        controllers.put("/user/login", LoginController.getInstance());
        controllers.put("/user/list", UserListController.getInstance());
        controllers.put("/index.html", IndexController.getInstance());
        controllers.put("/user/form.html", UserController.getInstance());
        controllers.put("/user/login.html", LoginController.getInstance());
        controllers.put("/user/login_failed.html", LoginFailController.getInstance());
    }

    public static HttpResponse serve(HttpRequest req) {
        if (req.path().extension().equals("html") || req.path().extension().isEmpty()) {
            return route(req);
        }
        return HttpResponse.staticFiles(req);
    }

    private static HttpResponse route(HttpRequest request) {
        String path = request.path().toString();
        if (controllers.containsKey(path)) {
            return controllers.get(path).service(request);
        }
        //TODO 에러 페이지로 이동
        return HttpResponse.NOT_FOUND;
    }
}
