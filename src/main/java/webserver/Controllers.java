package webserver;

import controller.Controller;
import controller.UserController;

import java.util.HashMap;
import java.util.Map;

public class Controllers {
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new UserController());
    }

    public static Controller getController(String path) {
        return controllers.get(path);
    }
}
