package webserver;

import webserver.controller.LoginController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.WelcomePageController;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static webserver.controller.AbstractController.ERROR_VIEW;

public class UrlMapper {
    private static Map<String, Controller> map = new HashMap<>();

    static {
        map.put(UserCreateController.PATH, UserCreateController.getInstance());
        map.put(WelcomePageController.PATH, WelcomePageController.getInstance());
        map.put(LoginController.PATH, LoginController.getInstance());
        map.put(UserListController.PATH, UserListController.getInstance());
    }

    public String service(HttpRequest request, HttpResponse response) {
        String url = request.getPath();
        if (map.containsKey(url)) {
            return getService(request, response, url);
        }

        return url;
    }

    private String getService(HttpRequest request, HttpResponse response, String url) {
        Controller controller = map.get(url);
        try {
            return controller.service(request, response);
        } catch (NotSupportedHttpMethodException e) {
            return ERROR_VIEW + "405";
        }
    }
}
