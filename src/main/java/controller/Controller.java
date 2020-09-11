package controller;

import http.HttpRequest;
import model.service.UserController;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Controller {
    private static final Map<ControllerMapper, BiConsumer<HttpRequest, DataOutputStream>> methods;

    static {
        methods = new HashMap<>();
        methods.put(ControllerMapper.CREATE_USER, UserController::saveUser);
    }

    public static BiConsumer<HttpRequest, DataOutputStream> getMethod(ControllerMapper controllerMapper) {
        return methods.get(controllerMapper);
    }
}
