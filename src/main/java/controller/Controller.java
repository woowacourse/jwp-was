package controller;

import model.service.UserController;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Controller {
    private static final Map<ControllerMapper, BiConsumer<Map<String, String>, DataOutputStream>> mappedMethods;

    static {
        mappedMethods = new HashMap<>();
        mappedMethods.put(ControllerMapper.CREATE_USER, UserController::saveUser);
    }

    public static BiConsumer<Map<String, String>, DataOutputStream> findMethod(ControllerMapper controllerMapper) {
        return mappedMethods.get(controllerMapper);
    }
}
