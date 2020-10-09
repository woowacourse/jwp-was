package webserver.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerFinder {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new GetUserListController());
    }

    private ControllerFinder() {
    }

    public static Controller findController(String resourceUri) {
        return controllers.getOrDefault(resourceUri, new ReadResourceController());
    }
}
