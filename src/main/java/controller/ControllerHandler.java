package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerHandler {
    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN = "/user/login";

    private Map<String, Controller> controllers;

    public ControllerHandler() {
        this.controllers = new HashMap<>();
        controllers.put(USER_CREATE_URL, new CreateUserController());
        controllers.put(USER_LOGIN, new UserLoginController());
    }

    public Optional<Controller> getController(String url) {
        if (!controllers.containsKey(url)) {
            return Optional.empty();
        }

        return Optional.of(controllers.get(url));
    }
}
