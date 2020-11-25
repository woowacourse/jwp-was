package domain.user.service;

import db.DataBase;
import domain.user.User;
import utils.UserMapper;
import webserver.request.HttpRequest;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    public static void createUser(HttpRequest httpRequest) {
        User user = UserMapper.createUser(httpRequest);
        DataBase.addUser(user);
    }

    public static Optional<User> findById(String id) {
        User user = DataBase.findUserById(id);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public static Collection<User> findAll() {
        return DataBase.findAll();
    }
}
