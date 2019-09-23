package service;

import db.DataBase;
import model.AuthorizationFailException;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public User login(String id, String password) {

        User user = DataBase.findUserById(id);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthorizationFailException();
        }

        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
