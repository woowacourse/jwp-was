package webserver.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerFinder {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    private ControllerFinder() {
    }

    public static Controller findController(String resourceUri) {
        return controllers.getOrDefault(resourceUri, new ReadResourceController());
    }
}
