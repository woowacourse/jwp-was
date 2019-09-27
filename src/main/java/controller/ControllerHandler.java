package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerHandler {
    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN_URL = "/user/login";
    private static final String USER_LIST_URL = "/user/list";

    private Map<String, Controller> controllers;

    public ControllerHandler() {
        this.controllers = new HashMap<>();
        controllers.put(USER_CREATE_URL, new CreateUserController());
        controllers.put(USER_LOGIN_URL, new UserLoginController());
        controllers.put(USER_LIST_URL, new UserListController());
    }

    public Optional<Controller> getController(String url) {
        if (!controllers.containsKey(url)) {
            return Optional.empty();
        }

        return Optional.of(controllers.get(url));
    }
}
