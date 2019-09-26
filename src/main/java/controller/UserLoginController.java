package controller;

import db.DataBase;
import http.common.CookieParser;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import model.User;

import static java.util.Objects.nonNull;

public class UserLoginController implements Controller {

    private static final String LOGIN_PATH = "/index.html";
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final RequestMapping LOGIN_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse("/user/login"));

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.findBodyParam("userId");
        String password = httpRequest.findBodyParam("password");
        User user = DataBase.findUserById(userId);

        if (isLoginSuccess(user, password)) {
            httpResponse.addCookie(CookieParser.parse("logined=true; Path=/"));
            httpResponse.redirect(LOGIN_PATH);
            return;
        }

        httpResponse.addCookie(CookieParser.parse("logined=false"));
        httpResponse.redirect(LOGIN_FAIL_PATH);
    }

    private boolean isLoginSuccess(final User user, final String password) {
        return nonNull(user) && user.isPasswordEquals(password);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return LOGIN_REQUEST_MAPPING.equals(requestMapping);
    }
}
