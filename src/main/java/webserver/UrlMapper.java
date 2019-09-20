package webserver;

import webserver.controller.UserCreateController;
import webserver.controller.WelcomePageController;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static Map<String, Controller> map = new HashMap<>();

    static {
        map.put(UserCreateController.PATH, UserCreateController.getInstance());
        map.put(WelcomePageController.PATH, WelcomePageController.getInstance());
    }

    public String service(HttpRequest request) {
        String url = request.getPath();
        if (map.containsKey(url)) {
            return getService(request, url);
        }

        return url;
    }

    private String getService(HttpRequest request, String url) {
        Controller controller = map.get(url);
        return controller.service(request);
    }
}
