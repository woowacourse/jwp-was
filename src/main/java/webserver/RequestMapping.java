package webserver;

import java.util.HashMap;
import java.util.Map;

import web.controller.Controller;
import web.controller.CreateUserController;
import web.controller.LoginController;
import web.controller.UserListController;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    public static Controller getController(String url) {
        return controllers.get(url);
    }
}
