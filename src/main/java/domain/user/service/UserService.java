package domain.user.service;

import db.DataBase;
import domain.user.User;
import utils.UserMapper;
import webserver.request.HttpRequest;

public class UserService {

    public static void createUser(HttpRequest httpRequest) {
        User user = UserMapper.createUser(httpRequest);
        DataBase.addUser(user);
    }

    public static User findUser(HttpRequest httpRequest) {
        User user = UserMapper.createUser(httpRequest);
        return DataBase.findUserById(user.getUserId());
    }
}
