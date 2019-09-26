package webserver.controller;

import http.request.HttpRequest;

import java.util.Map;
import java.util.Optional;

public class ControllerFinder {
    Map<String, Controller> uriConfig;

    public ControllerFinder(Map<String, Controller> uriConfig) {
        this.uriConfig = uriConfig;
    }

    public Controller find(HttpRequest httpRequest) {
        return Optional.ofNullable(uriConfig.get(httpRequest.getResourcePath()))
                .orElseThrow(() -> new IllegalArgumentException("Url에 해당하는 컨트롤러 X"));
    }
}
