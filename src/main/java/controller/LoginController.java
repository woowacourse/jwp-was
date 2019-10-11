package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.QueryParams;
import http.response.HttpResponse;
import model.User;

import static http.HttpHeaders.SET_COOKIE;

public class LoginController extends AbstractController {
    public static final String LOGINED_TRUE = "logined=true; Path=/";
    public static final String LOGINED_FALSE = "logined=false; Path=/";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = getUser(request);
        String inputPassword = getInputPassword(request);

        if (failLogin(user, inputPassword)) {
            response.addHeader(SET_COOKIE, LOGINED_FALSE);
            response.redirect("/user/login_failed.html");
            return;
        }
        response.addHeader(SET_COOKIE, LOGINED_TRUE);
        response.redirect("/index.html");
        return;
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
