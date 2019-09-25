package webserver.controller;

import http.HttpRequest;

import java.util.Map;
import java.util.Optional;

public class ControllerFinder {
    Map<String, Controller> controllerUriConfig;

    public ControllerFinder(Map<String, Controller> controllerUriConfig) {
        this.controllerUriConfig = controllerUriConfig;
    }

    public Controller find(HttpRequest httpRequest) {
        return Optional.ofNullable(controllerUriConfig.get(httpRequest.getResourcePath()))
                .orElseGet(FileController::new);
    }
}
