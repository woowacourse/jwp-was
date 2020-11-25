package domain.user.service;

import db.DataBase;
import domain.user.User;
import utils.UserMapper;
import webserver.request.HttpRequest;

import java.util.Collection;

public class UserService {

    public static void createUser(HttpRequest httpRequest) {
        User user = UserMapper.createUser(httpRequest);
        DataBase.addUser(user);
    }

    public static User findById(String id) {
        return DataBase.findUserById(id);
    }

    public static Collection<User> findAll() {
        return DataBase.findAll();
    }
}
