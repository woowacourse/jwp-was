package controller;

import service.UserService;
import webserver.Request;

public class UserController {

    public static String getCreateUser(Request request) {
        String input = request.extractQueryString();
        UserService.createUser(input);
        return "./templates/index.html";
    }

    public static String postCreateUser(Request request) {
        String input = request.extractBody();
        UserService.createUser(input);
        return "./templates/index.html";
    }
}
