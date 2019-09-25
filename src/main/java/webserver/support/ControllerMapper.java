package webserver.support;

import webserver.controller.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerMapper {
    private Map<String, Controller> api;

    public ControllerMapper() {
        initialize();
    }

    private void initialize() {
        api = new HashMap<>();
        api.put("/user/create", CreateUserController.getInstance());
        api.put("/user/login", LoginController.getInstance());
        api.put("/user/list", UserListController.getInstance());
    }

    public Controller map(String url) {
        return Optional.ofNullable(api.get(url)).orElseGet(FileController::getInstance);
    }
}
