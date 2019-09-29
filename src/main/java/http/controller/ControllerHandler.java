package http.controller;

import http.model.HttpRequest;
import http.model.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerHandler {
    private Map<ControllerMapping, Controller> controllers;

    public ControllerHandler() {
        controllers = new HashMap<>();
    }

    public void addController(ControllerMapping mapping, Controller handler) {
        controllers.put(mapping, handler);
    }

    public HttpResponse doService(HttpRequest httpRequest) {
        return controllers.get(resolveRequestMapping(getCandidate(httpRequest))).service(httpRequest);
    }

    private List<ControllerMapping> getCandidate(HttpRequest httpRequest) {
        return controllers.keySet().stream()
                .filter(key -> key.match(httpRequest))
                .sorted()
                .collect(Collectors.toList());
    }

    private ControllerMapping resolveRequestMapping(List<ControllerMapping> controllerMappings) {
        if (controllerMappings.isEmpty()) {
            throw new NotFoundException();
        }
        return controllerMappings.get(0);
    }
}
