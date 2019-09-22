package webserver;

import http.controller.Controller;
import http.controller.CreateUserController;
import http.controller.ResourcesController;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class RequestMapper {
    private static final Map<String, Controller> controllers = new HashedMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    public static Controller mappingController(String path) {
        return controllers.getOrDefault(path, new ResourcesController());
    }
}
