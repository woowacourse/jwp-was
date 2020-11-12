package controller;

import db.DataBase;
import http.HttpRequest;
import model.User;

public class UserCreateController {
    public static void createUser(HttpRequest httpRequest) {
        String userId = httpRequest.getParam("userId");
        String password = httpRequest.getParam("password");
        String name = httpRequest.getParam("name");
        String email = httpRequest.getParam("email");
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }
}
