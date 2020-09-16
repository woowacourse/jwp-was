package domain.user.service;

import db.DataBase;
import domain.user.User;
import utils.RequestUtils;

public class UserService {

    public static void createUser(String input) {
        User user = RequestUtils.createUser(input);
        DataBase.addUser(user);
    }
}
