package webserver;

import controller.Controller;
import controller.IndexController;
import controller.LoginController;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private static final Controller INDEX_CONTROLLER = new IndexController();
    private static final Controller USER_CONTROLLER = new UserController();
    private static final Controller LOGIN_CONTROLLER = new LoginController();

    private static final Map<String, Controller> controllers = new HashMap<>();
    static {
        controllers.put("/", INDEX_CONTROLLER);
        controllers.put("/user/create", USER_CONTROLLER);
        controllers.put("/user/login", LOGIN_CONTROLLER);
        controllers.put("/index.html", INDEX_CONTROLLER);
        controllers.put("/user/form.html", USER_CONTROLLER);
        controllers.put("/user/login.html", LOGIN_CONTROLLER);
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
