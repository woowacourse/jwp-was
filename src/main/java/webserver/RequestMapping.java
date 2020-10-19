package webserver;

import java.util.HashMap;
import java.util.Map;

import web.controller.Controller;
import web.controller.CreateUserController;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    public static Controller getController(String url) {
        return controllers.get(url);
    }
}
