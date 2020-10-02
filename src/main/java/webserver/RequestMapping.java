package webserver;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import controller.ResourceController;
import controller.UserCreateController;

public class RequestMapping {
    private final static ResourceController RESOURCE_CONTROLLER = new ResourceController();
    private final static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/users", new UserCreateController());
    }

    public static Controller getController(String requestUrl) {
        Controller controller = controllers.get(requestUrl);

        if (controller != null) {
            return controller;
        }

        return RESOURCE_CONTROLLER;
    }
}
