package service;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserService {
    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    public void createUser(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getBodyValue("userId"),
                httpRequest.getBodyValue("password"),
                httpRequest.getBodyValue("name"),
                httpRequest.getBodyValue("email")
        );
        DataBase.addUser(user);
    }

    public boolean loginUser(HttpRequest httpRequest) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");
        User user = DataBase.findUserById(userId);

        if (user != null && user.isMatch(password)) {
            return true;
        }
        return false;
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
