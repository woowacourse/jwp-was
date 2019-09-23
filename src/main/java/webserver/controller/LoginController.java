package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;

public class LoginController extends AbstractController {
    public static final String PATH = "/user/login";
    private static final LoginController INSTANCE = new LoginController();

    public static LoginController getInstance() {
        return INSTANCE;
    }

    @Override
    protected String doPost(HttpRequest httpRequest) {
        String userId = httpRequest.getParam("userId");
        String password = httpRequest.getParam("password");
        User user = DataBase.findUserById(userId);
        if (validateUser(password, user)) {
            return "/redirect:/index.html";
        }
        return "/redirect:/user/login_failed.html";
    }

    private boolean validateUser(String password, User user) {
        return isExistUser(user) && checkPassword(password, user);
    }

    private boolean checkPassword(String password, User user) {
        return user.checkPassword(password);
    }

    private boolean isExistUser(User user) {
        return user != null;
    }
}
