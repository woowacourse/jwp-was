package webserver;

import http.controller.Controller;
import http.controller.UserCreateController;
import http.controller.ResourcesController;
import http.controller.UserLoginController;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class RequestMapper {
    private static final Map<String, Controller> controllers = new HashedMap<>();

    static {
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/user/login", new UserLoginController());
    }

    public static Controller mappingController(String path) {
        return controllers.getOrDefault(path, new ResourcesController());
    }
}
