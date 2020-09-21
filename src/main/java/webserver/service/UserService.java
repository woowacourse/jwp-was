package webserver.service;

import db.DataBase;
import model.User;

public class UserService {
    public static void create(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }
}
