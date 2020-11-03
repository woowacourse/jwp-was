package controller;

import db.DataBase;
import java.util.Map;
import model.User;

public class UserController {

    public static User create(final Map<String, String> contents) {
        final String userId = contents.get("userId");
        final String password = contents.get("password");
        final String name = contents.get("name");
        final String email = contents.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        return user;
    }
}
