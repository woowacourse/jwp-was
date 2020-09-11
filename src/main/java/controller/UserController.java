package controller;

import db.DataBase;
import model.User;
import utils.RequestUtils;
import webserver.Request;

public class UserController {

    public static String createUser(Request request) {
        String input = request.extractQueryString();
        User user = RequestUtils.createUser(input);
        DataBase.addUser(user);
        return "./templates/index.html";
    }
}
