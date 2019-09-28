package service;

import db.DataBase;
import db.exception.UserNotFoundException;
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
        try {
            User user = DataBase.findUserById(userId);
            String password = httpRequest.getBodyValue("password");
            return user.getPassword().equals(password);
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
