package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import controller.Controller;
import controller.IndexController;
import controller.ResourceController;
import controller.UserCreateController;

class RequestMapping {
    private final static ResourceController RESOURCE_CONTROLLER = new ResourceController();
    private final static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/users", new UserCreateController());
        controllers.put("/", new IndexController());
    }

    public static Controller getController(String requestUrl) {
        Controller controller = controllers.get(requestUrl);

        if (Objects.nonNull(controller)) {
            return controller;
        }

        return RESOURCE_CONTROLLER;
    }
}
