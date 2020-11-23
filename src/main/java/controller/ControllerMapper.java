package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerMapper {
    private static final Map<String, Controller> controllers = new HashMap<>();
    private static final Controller defaultController = new ResourceController();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new UserListController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/", new IndexController());
    }

    private ControllerMapper() {
    }

    public static Controller mappingControllerByPath(String path) {
        Controller controller = controllers.get(path);

        if (Objects.isNull(controller)) {
            return defaultController;
        }
        return controller;
    }
}
