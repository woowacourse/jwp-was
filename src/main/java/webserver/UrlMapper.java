package webserver;

import webserver.controller.*;
import webserver.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static Map<String, Controller> map = new HashMap<>();

    static {
        map.put(UserCreateController.PATH, new UserCreateController());
        map.put(WelcomePageController.PATH, new WelcomePageController());
        map.put(LoginController.PATH, new LoginController());
        map.put(UserListController.PATH, new UserListController());
    }

    public Controller service(HttpRequest request) {
        String url = request.getPath();
        if (map.containsKey(url)) {
            return map.get(url);
        }
        return ErrorViewController.getInstance();
    }
}
