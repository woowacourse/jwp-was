package webserver;

import controller.Controller;
import controller.IndexController;
import controller.UserController;
import controller.UserFormController;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static final Map<String, Controller> HANDLER_MAP = new HashMap<>();

    static {
        HANDLER_MAP.put("/user/create", UserController.getInstance());
        HANDLER_MAP.put("/user/form", UserFormController.getInstance());
        HANDLER_MAP.put("/", IndexController.getInstance());
    }

    public Controller getHandler(String path) {
        return HANDLER_MAP.get(path);
    }

    private static class HandlerMappingLazyHolder {
        private static final HandlerMapping INSTANCE = new HandlerMapping();
    }

    public static HandlerMapping getInstance() {
        return HandlerMappingLazyHolder.INSTANCE;
    }
}
