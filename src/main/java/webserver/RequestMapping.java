package webserver;

import controller.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class RequestMapping {

    private static final ResourceController RESOURCE_CONTROLLER = new ResourceController();
    private static final Map<String, Controller> CONTROLLERS = new HashMap<>();

    static {
        CONTROLLERS.put("/user/list", new UserListController());
        CONTROLLERS.put("/user/login", new LoginController());
        CONTROLLERS.put("/users", new UserCreateController());
        CONTROLLERS.put("/", new IndexController());
    }

    public static Controller getController(String requestUrl) {
        Controller controller = CONTROLLERS.get(requestUrl);

        if (Objects.nonNull(controller)) {
            return controller;
        }

        return RESOURCE_CONTROLLER;
    }
}
