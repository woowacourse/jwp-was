package controller;

import db.DataBase;
import http.common.Cookie;
import http.parser.HttpUriParser;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import static java.util.Objects.nonNull;

public class UserLoginController implements Controller {

    private static final String INDEX_PATH = "/index.html";
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final String USER_LOGIN_URI = "/user/login";
    private static final RequestMapping LOGIN_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse(USER_LOGIN_URI));

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.findBodyParam("userId");
        String password = httpRequest.findBodyParam("password");
        User user = DataBase.findUserById(userId);

        if (isValidUser(user, password)) {
            httpResponse.addCookie(new Cookie("logined","true"));
            httpResponse.redirect(INDEX_PATH);
            return;
        }

        httpResponse.redirect(LOGIN_FAIL_PATH);
    }

    private boolean isValidUser(final User user, final String password) {
        return nonNull(user) && user.isPasswordEquals(password);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return LOGIN_REQUEST_MAPPING.equals(requestMapping);
    }
}
