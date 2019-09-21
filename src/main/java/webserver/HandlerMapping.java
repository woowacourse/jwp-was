package webserver;

import controller.Controller;
import controller.CreateUserController;
import http.HttpRequest;
import webserver.exception.InvalidUriException;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static Map<String, Controller> uriMapping = new HashMap<>();

    static {
        uriMapping.put("/user/create", new CreateUserController());
    }

    public static Controller handle(HttpRequest httpRequest) {
        if (!uriMapping.containsKey(httpRequest.getUri())) {
            throw new InvalidUriException();
        }

        return uriMapping.get(httpRequest.getUri());
    }
}