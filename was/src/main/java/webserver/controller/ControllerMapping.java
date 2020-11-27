package webserver.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import webserver.http.request.RequestMapping;

public class ControllerMapping {
    private static final Map<RequestMapping, Controller> requestMappings = new HashMap<>();

    public static void put(RequestMapping key, Controller value) {
        requestMappings.put(key, value);
    }

    public static Optional<Controller> get(RequestMapping requestMapping) {
        if (requestMappings.containsKey(requestMapping)) {
            return Optional.of(requestMappings.get(requestMapping));
        }
        return Optional.empty();
    }
}
