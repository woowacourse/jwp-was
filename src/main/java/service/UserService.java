package service;

import db.DataBase;
import http.request.HttpRequest;
import model.User;

public class UserService {

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    public void create(HttpRequest httpRequest) {
        User user = new User(httpRequest.getHttpRequestBodyByName("userId"), httpRequest.getHttpRequestBodyByName("password"), httpRequest.getHttpRequestBodyByName("name"), httpRequest
                .getHttpRequestBodyByName("email"));
        DataBase.addUser(user);
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
