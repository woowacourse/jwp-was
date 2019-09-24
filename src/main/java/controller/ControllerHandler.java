package controller;

import exception.NotFoundUrlException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerHandler {
    private static final String USER_CREATE_URL = "/user/create";

    private Map<String, Controller> controllers;

    public ControllerHandler() {
        this.controllers = new HashMap<>();
        controllers.put(USER_CREATE_URL, new CreateUserController());
    }

    public Optional<Controller> getController(String url) {
        if (!controllers.containsKey(url)) {
            return Optional.empty();
        }

        return Optional.of(controllers.get(url));
    }
}
