package service;

import db.DataBase;
import http.HttpRequest;
import model.User;

public class UserService {
    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void createUser(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getBodyValue("userid"),
                httpRequest.getBodyValue("password"),
                httpRequest.getBodyValue("name"),
                httpRequest.getBodyValue("email")
        );
        DataBase.addUser(user);
    }

    public boolean authenticateUser(HttpRequest httpRequest) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");
        User userById = DataBase.findUserById(userId);
        if (userById == null) {
            return false;
        }
        return userById.getPassword().equals(password);
    }
}
