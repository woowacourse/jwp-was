package service;

import db.DataBase;
import http.HttpRequest;
import model.User;

public class UserService {
    public void createUser(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getBodyValue("userid"),
                httpRequest.getBodyValue("password"),
                httpRequest.getBodyValue("name"),
                httpRequest.getBodyValue("email")
        );
        DataBase.addUser(user);
    }
}
