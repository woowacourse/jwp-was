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

    public boolean loginUser(HttpRequest httpRequest) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");
        User user = DataBase.findUserById(userId);

        return user != null && user.isMatch(password);
    }

    public User getUser(HttpRequest httpRequest) {
        String userId = httpRequest.getBodyValue("userId");
        return DataBase.findUserById(userId);
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
