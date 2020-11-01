package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerFinder {
    private static final Map<String, Controller> controllers = new HashMap<>();
    private static final String DEFAULT_PATH = "/**";

    private ControllerFinder() {
    }

    public static void addDefaultController(Controller controller) {
        controllers.put(DEFAULT_PATH, controller);
    }

    public static void addController(String path, Controller controller) {
        controllers.put(path, controller);
    }

    public static Controller findController(String resourceUri) {
        return controllers.getOrDefault(resourceUri, controllers.get(DEFAULT_PATH));
    }
}
