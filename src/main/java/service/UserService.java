package service;

import db.DataBase;
import http.RequestBody;
import model.User;

public class UserService {
    public void createUser(RequestBody requestBody) {
        User user = new User(
                requestBody.getValue("userId"),
                requestBody.getValue("password"),
                requestBody.getValue("name"),
                requestBody.getValue("email")
        );
        DataBase.addUser(user);
    }
}
