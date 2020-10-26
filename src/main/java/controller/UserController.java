package controller;

import db.DataBase;
import java.util.Map;
import model.User;

public class UserController {

    public static User create(final Map<String, String> queryParams) {
        final String userId = queryParams.get("userId");
        final String password = queryParams.get("password");
        final String name = queryParams.get("name");
        final String email = queryParams.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        return user;
    }
}
