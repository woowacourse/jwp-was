package webserver;

import http.controller.*;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class RequestMapper {
    private static final Map<String, Controller> controllers = new HashedMap<>();

    static {
        controllers.put(UserCreateController.URL, new UserCreateController());
        controllers.put(UserLoginController.URL, new UserLoginController());
        controllers.put(UserListController.URL, new UserListController());
    }

    public static Controller mappingController(String path) {

        return controllers.getOrDefault(path, new ResourcesController());
    }
}
