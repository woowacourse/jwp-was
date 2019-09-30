package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.LoginController;
import controller.UserListController;
import http.HttpRequest;
import webserver.exception.InvalidUriException;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static final Map<String, Controller> uriMapping = new HashMap<>();

    static {
        uriMapping.put("/user/create", new CreateUserController());
        uriMapping.put("/user/login", new LoginController());
        uriMapping.put("/user/list", new UserListController());
    }

    public static Controller handle(HttpRequest httpRequest) {
        if (!uriMapping.containsKey(httpRequest.getUri())) {
            throw new InvalidUriException();
        }

        return uriMapping.get(httpRequest.getUri());
    }
}