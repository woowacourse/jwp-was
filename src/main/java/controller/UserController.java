package controller;

import http.HttpRequest;
import model.User;

public class UserController {
    public static User createUser(HttpRequest httpRequest) {
        String userId = httpRequest.getParam("userId");
        String password = httpRequest.getParam("password");
        String name = httpRequest.getParam("name");
        String email = httpRequest.getParam("email");
        return new User(userId, password, name, email);
    }
}
