package controller;

import model.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Controller {
    private static final Map<ControllerMapper, Consumer<Map<String, String>>> methods;

    static {
        methods = new HashMap<>();
        methods.put(ControllerMapper.CREATE_USER, UserService::saveUser);
    }

    public static Consumer<Map<String, String>> getMethod(ControllerMapper controllerMapper) {
        return methods.get(controllerMapper);
    }
}
