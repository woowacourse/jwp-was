package model.service;

import model.db.DataBase;
import model.domain.User;

import java.util.Map;

public class UserService {
    public static void saveUser(Map<String, String> params) {
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        DataBase.addUser(user);
    }
}
