package webserver;

import controller.Controller;
import controller.IndexController;
import controller.UserController;
import controller.UserFormController;
import webserver.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HandlerMapping {
    private static final Map<String, Controller> HANDLER_MAP = new HashMap<>();

    static {
        HANDLER_MAP.put("/user/create", UserController.getInstance());
        HANDLER_MAP.put("/user/form", UserFormController.getInstance());
        HANDLER_MAP.put("/", IndexController.getInstance());
    }

    public Controller getHandler(String path) {
        return Optional.ofNullable(HANDLER_MAP.get(path))
                .orElseThrow(ResourceNotFoundException::new);
    }

    private static class HandlerMappingLazyHolder {
        private static final HandlerMapping INSTANCE = new HandlerMapping();
    }

    public static HandlerMapping getInstance() {
        return HandlerMappingLazyHolder.INSTANCE;
    }
}
