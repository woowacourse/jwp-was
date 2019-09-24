package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginController extends AbstractController {
    public static final String PATH = "/user/login";
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
            setLoginSuccessCookie(response);
            return "/redirect:/index.html";
        }
        setLoginFailedCookie(response);
        return "/redirect:/user/login_failed.html";
    }

    private void setLoginFailedCookie(HttpResponse response) {
        response.setCookie("logined=false; Path=/");
    }

    private void setLoginSuccessCookie(HttpResponse response) {
        response.setCookie("logined=true; Path=/");
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
