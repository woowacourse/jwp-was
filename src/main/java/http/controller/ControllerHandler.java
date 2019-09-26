package http.controller;

import http.model.HttpRequest;
import http.model.HttpResponse;
import http.supoort.ControllerMapping;
import http.supoort.NotSupportedRequestException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerHandler {
    private Map<ControllerMapping, Controller> handlers;

    public ControllerHandler() {
        handlers = new HashMap<>();
    }

    public void addController(ControllerMapping mapping, Controller handler) {
        handlers.put(mapping, handler);
    }

    public HttpResponse doService(HttpRequest httpRequest) {
        return handlers.get(resolveRequestMapping(getCandidate(httpRequest))).service(httpRequest);
    }

    private List<ControllerMapping> getCandidate(HttpRequest httpRequest) {
        return handlers.keySet().stream()
                .filter(key -> key.match(httpRequest))
                .sorted()
                .collect(Collectors.toList());
    }

    private ControllerMapping resolveRequestMapping(List<ControllerMapping> controllerMappings) {
        if (controllerMappings.isEmpty()) {
            throw new NotSupportedRequestException();
        }
        return controllerMappings.get(0);
    }
}
