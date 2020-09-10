package service;

import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import http.request.Request;
import model.User;

public class UserService {

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    public void create(Request request) {
        User user = new User(request.getBody("userId"), request.getBody("password"), request.getBody("name"), request.getBody("email"));
        DataBase.addUser(user);
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
