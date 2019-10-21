package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.QueryParams;
import http.response.HttpResponse;
import model.User;

public class LoginController extends AbstractController {
    static final String LOGINED = "logined";
    static final String TRUE = "true";
    static final String FALSE = "false";
    private static final String PATH = "Path";
    private static final String ALL = "/";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = getUser(request);
        String inputPassword = getInputPassword(request);

        if (failLogin(user, inputPassword)) {
            response.addCookie(LOGINED, FALSE);
            response.redirect("/user/login_failed.html");
            return;
        }
        response.addCookie(LOGINED, TRUE);
        response.addCookie(PATH, ALL);
        response.redirect("/index.html");
    }

    private boolean failLogin(User user, String inputPassword) {
        return (user == null) || !user.hasSamePasswordWith(inputPassword);
    }

    private User getUser(HttpRequest request) {
        QueryParams queryParams = request.getQueryParams();
        String userId = queryParams.getParam("userId").trim();
        return DataBase.findUserById(userId);
    }

    private String getInputPassword(HttpRequest request) {
        QueryParams queryParams = request.getQueryParams();
        return queryParams.getParam("password").trim();
    }
}
