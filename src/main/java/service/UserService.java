package service;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;

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

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
