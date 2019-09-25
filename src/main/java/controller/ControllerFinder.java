package controller;

import controller.user.UserListController;
import controller.user.UserLoginController;
import controller.user.UserSignUpController;

import java.util.HashMap;
import java.util.Map;

public class ControllerFinder {

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/index.html", new IndexController());
        controllers.put("/user/create", new UserSignUpController());
        controllers.put("/user/login", new UserLoginController());
        controllers.put("/user/list.html", new UserListController());
    }

    private ControllerFinder() {
    }

    public static Controller findController(String uri) {
        if (isInvalidUri(uri)) {
            return new ResourceController();
        }
        return controllers.get(uri);
    }

    private static boolean isInvalidUri(final String uri) {
        return !controllers.containsKey(uri);
    }
}
