package webserver.support;

import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.FileController;

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
    }

    public Controller map(String url) {
        return Optional.ofNullable(api.get(url)).orElseGet(FileController::getInstance);
    }
}
