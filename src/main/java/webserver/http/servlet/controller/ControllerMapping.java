package webserver.http.servlet.controller;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpMethod;
import webserver.http.request.RequestMapping;

public class ControllerMapping {
    private static final Map<RequestMapping, Controller> requestMappings = new HashMap<>();

    static {
        requestMappings.put(new RequestMapping("/user/create", HttpMethod.POST),
                new UserCreateController());
    }

    public static Controller get(RequestMapping requestMapping) {
        if (requestMappings.containsKey(requestMapping)) {
            return requestMappings.get(requestMapping);
        }
        return new ResourceController();
    }
}
