package service;

import db.DataBase;
import http.request.Request;
import model.User;

public class UserService {

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    public void create(Request request) {
        User user = new User(request.getBodyByName("userId"), request.getBodyByName("password"), request.getBodyByName("name"), request.getBodyByName("email"));
        DataBase.addUser(user);
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
