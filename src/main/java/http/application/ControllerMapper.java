package http.application;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {
    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    public static Controller controllerMapping(String url) {
        Controller controller = controllers.get(url);

        if (controller == null) {
            return new BasicController();
        }
        return controller;
    }
}
