package webserver.http.servlet.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import webserver.http.request.HttpMethod;
import webserver.http.request.RequestMapping;

public class ControllerMapping {
    private static final Map<RequestMapping, Controller> requestMappings = new HashMap<>();

    static {
        requestMappings.put(new RequestMapping("/user/create", HttpMethod.POST),
                new UserCreateController());
        requestMappings.put(new RequestMapping("/user/login", HttpMethod.POST),
                new UserLoginController());
    }

    public static Optional<Controller> get(RequestMapping requestMapping) {
        if (requestMappings.containsKey(requestMapping)) {
            return Optional.of(requestMappings.get(requestMapping));
        }
        return Optional.empty();
    }
}
