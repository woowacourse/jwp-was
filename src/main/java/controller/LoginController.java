package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

import java.util.Map;

public class LoginController extends AbstractController {

    private static final String USER_LOGIN_URL = "/user/login";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_COOKIE_KEY = "logined";

    private static boolean verifyUser(Map<String, String> parsedBody, User found) {
        return found != null && found.matchPassword(parsedBody.get(PASSWORD));
    }

    @Override
    public void doGet(HttpRequest req, HttpResponse res) {
        throw createUnsupportedException();
    }

    @Override
    public void doPost(HttpRequest req, HttpResponse response) {
        Map<String, String> parsedBody = UrlEncodedParser.parse(new String(req.getBody()));
        User found = DataBase.findUserById(parsedBody.get(USER_ID));

        String redirectUrl = "/user/login_failed.html";

        if (verifyUser(parsedBody, found)) {
            redirectUrl = "/index.html";
            req.getSession().setAttribute("logined", "true");
        }

        response.setStatus(HttpStatus.FOUND);
        response.addHeader("Location", redirectUrl);
    }

    @Override
    public String getPath() {
        return USER_LOGIN_URL;
    }
}
