package webserver;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import controller.StaticController;
import controller.TemplatesController;
import controller.UserCreateController;

public class RequestMapping {
    private final static TemplatesController templatesController = new TemplatesController();
    private final static StaticController staticController = new StaticController();
    private final static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/users", new UserCreateController());
    }

    public static Controller getController(String requestUrl) {
        Controller controller = controllers.get(requestUrl);

        if (controller != null) {
            return controller;
        }
        
        return staticController;
    }
}
