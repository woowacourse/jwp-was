package service;

import java.util.HashMap;

import db.DataBase;
import model.User;
import utils.RequestUtils;

public class UserService {

    public static void signIn(String userInfoUrl) {
        HashMap<String, String> signInInfo = RequestUtils.parseUserInfo(userInfoUrl);
        User user = new User(signInInfo.get("userId"),signInInfo.get("password")
                ,signInInfo.get("name"),signInInfo.get("email"));

        DataBase.addUser(user);
    }


}
