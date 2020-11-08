package controller;

import java.util.Map;

import http.HttpRequest;
import model.User;

public class UserController {
    public static User createUser(HttpRequest httpRequest) {
        Map<String, String> userInfo = httpRequest.getBody();
        String userId = userInfo.get("userId");
        String password = userInfo.get("password");
        String name = userInfo.get("name");
        String email = userInfo.get("email");
        return new User(userId, password, name, email);
    }
}
