package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Objects;

public class LoginController extends AbstractController {
    public static final String PATH = "/user/login";
    public static final String LOGINED = "logined";
    private static final LoginController INSTANCE = new LoginController();

    public static LoginController getInstance() {
        return INSTANCE;
    }

    @Override
    protected String doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getParam("userId");
        String password = request.getParam("password");
        User user = DataBase.findUserById(userId);
        if (validateUser(password, user)) {
            setSession(request, response, LOGINED, "true");
            return REDIRECT_VIEW + "/index.html";
        }
        setSession(request, response, LOGINED, "false");
        return REDIRECT_VIEW + "/user/login_failed.html ";
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
