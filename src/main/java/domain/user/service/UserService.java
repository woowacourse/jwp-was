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
}
