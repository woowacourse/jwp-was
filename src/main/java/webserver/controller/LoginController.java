package webserver.controller;

import db.DataBase;
import model.User;
import webserver.View;
import webserver.http.HttpRequest;

import java.util.Objects;

public class LoginController extends AbstractController {
    public static final String PATH = "/user/login";
    public static final String LOGINED = "logined";

    @Override
    protected View doPost(HttpRequest request) {
        String userId = request.getParam("userId");
        String password = request.getParam("password");
        User user = DataBase.findUserById(userId);
        if (validateUser(password, user)) {
            request.addSessionAttr(LOGINED, "true");
            return new View(REDIRECT_VIEW + "/index.html");
        }
        request.addSessionAttr(LOGINED, "false");
        return new View(REDIRECT_VIEW + "/user/login_failed.html");
    }

    private boolean validateUser(String password, User user) {
        return isExistUser(user) && checkPassword(password, user);
    }

    private boolean checkPassword(String password, User user) {
        return user.checkPassword(password);
    }

    private boolean isExistUser(User user) {
        return Objects.nonNull(user);
    }
}
