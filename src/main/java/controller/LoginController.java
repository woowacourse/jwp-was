package controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public class LoginController extends AbstractController {

    private static final String USER_LOGIN_URL = "/user/login";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_ATTR_KEY = "logined";

    private static boolean verifyUser(Map<String, String> parsedBody, User found) {
        return found != null && found.verify(parsedBody.get(PASSWORD));
    }

    @Override
    public void doGet(HttpRequest req, HttpResponse res) {
        throw createUnsupportedException();
    }

    @Override
    public void doPost(HttpRequest req, HttpResponse response) {
        Map<String, String> parsedBody = (Map<String, String>) req.getBody();
        User found = DataBase.findUserById(parsedBody.get(USER_ID));

        String redirectUrl = "/user/login_failed.html";

        if (verifyUser(parsedBody, found)) {
            redirectUrl = "/index.html";
            response.addCookie(LOGINED_ATTR_KEY, "true");
        }

        response.redirect(redirectUrl);
    }

    @Override
    public String getPath() {
        return USER_LOGIN_URL;
    }
}
