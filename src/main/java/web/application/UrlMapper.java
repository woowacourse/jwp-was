package web.application;

import java.util.HashMap;
import java.util.Map;

import web.application.controller.Controller;
import web.application.controller.CreateUserController;
import web.application.controller.RootController;
import web.server.exception.UrlNotFoundException;

public class UrlMapper {

    private static final Map<String, Controller> mapper;

    static {
        mapper = new HashMap<>();
        mapper.put("/user/create", CreateUserController.getInstance());
        mapper.put("/", RootController.getInstance());
    }

    public static Controller findMatchingController(String path) {
        if (mapper.containsKey(path)) {
            return mapper.get(path);
        }
        throw new UrlNotFoundException(path);
    }
}
