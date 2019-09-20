package service;

import db.DataBase;
import model.AuthorizationFailException;
import model.User;

public class UserService {

    public User login(String id, String password) {

        User user = DataBase.findUserById(id);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthorizationFailException();
        }

        return user;
    }
}
