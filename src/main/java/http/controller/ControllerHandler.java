package http.controller;

import http.model.HttpRequest;
import http.model.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerHandler {
    private Map<ControllerMapping, Controller> controllers;

    public ControllerHandler() {
        controllers = new HashMap<>();
    }

    public void addController(ControllerMapping mapping, Controller handler) {
        controllers.put(mapping, handler);
    }

    public HttpResponse doService(HttpRequest httpRequest) {
        return controllers.get((getCandidate(httpRequest))).service(httpRequest);
    }

    private ControllerMapping getCandidate(HttpRequest httpRequest) {
        return controllers.keySet().stream()
                .filter(key -> key.match(httpRequest))
                .sorted()
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
