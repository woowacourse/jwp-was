package controller;

import http.request.RequestParams;
import model.service.UserService;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Controller {
    private static final Map<ControllerMapper, BiConsumer<RequestParams, DataOutputStream>> mappedMethods;

    static {
        mappedMethods = new HashMap<>();
        mappedMethods.put(ControllerMapper.CREATE_USER, UserService::saveUser);
    }

    public static BiConsumer<RequestParams, DataOutputStream> findMethod(ControllerMapper controllerMapper) {
        return mappedMethods.get(controllerMapper);
    }
}
