package domain.user.service;

import db.DataBase;
import domain.user.User;
import utils.UserMapper;

import java.util.Map;

public class UserService {

    public static void createUser(Map<String, String> inputs) {
        User user = UserMapper.createUser(inputs);
        DataBase.addUser(user);
        System.out.println(DataBase.findAll());
    }
}
