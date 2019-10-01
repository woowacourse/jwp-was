package webserver;

import webserver.controller.LoginController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.WelcomePageController;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import static webserver.controller.AbstractController.ERROR_VIEW;

public class UrlMapper {
    private static Map<String, Controller> map = new HashMap<>();

    static {
        map.put(UserCreateController.PATH, new UserCreateController());
        map.put(WelcomePageController.PATH, new WelcomePageController());
        map.put(LoginController.PATH, new LoginController());
        map.put(UserListController.PATH, new UserListController());
    }

    public View service(HttpRequest request) {
        String url = request.getPath();
        if (map.containsKey(url)) {
            return getService(request, url);
        }

        return new View(url);
    }

    private View getService(HttpRequest request, String url) {
        Controller controller = map.get(url);
        try {
            return controller.service(request);
        } catch (NotSupportedHttpMethodException e) {
            return new View(ERROR_VIEW + "405");
        }
    }
}
