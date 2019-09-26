package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.*;

import java.util.HashMap;
import java.util.Map;

public class LoginController extends AbstractController {

    private static final String USER_LOGIN_URL = "/user/login";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_COOKIE_KEY = "logined";

    public LoginController() {
        methods = new HashMap<>();
        methods.put(new RequestMapping(HttpMethod.POST, USER_LOGIN_URL), this::doPost);
    }

    private Response doPost(Request request) {
        Map<String, String> parsedBody = UrlEncodedParser.parse(new String(request.getBody()));
        User user = DataBase.findUserById(parsedBody.get(USER_ID));

        String redirectUrl = "/user/login_failed.html";
        String loginedCookie = CookieLoginStatus.False.getText();

        if (verify(user, parsedBody.get(PASSWORD))) {
            redirectUrl = "/index.html";
            loginedCookie = CookieLoginStatus.TRUE.getText();
        }

        return Response.ResponseBuilder.redirect(redirectUrl)
                .withCookie(LOGINED_COOKIE_KEY, loginedCookie)
                .build();
    }

    private boolean verify(User user, String password) {
        return user != null && user.matchPassword(password);
    }
}
