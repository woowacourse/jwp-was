package service;

import db.DataBase;
import model.User;
import utils.RequestUtils;

public class UserService {

    public static void createUser(String input) {
        User user = RequestUtils.createUser(input);
        DataBase.addUser(user);
    }
}
