package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import webserver.domain.Cookie;
import webserver.domain.QueryParameter;
import webserver.domain.Request;
import webserver.domain.Response;

import static org.slf4j.LoggerFactory.getLogger;

public class UserController {
    private static final Logger LOG = getLogger(UserController.class);
    private static final String URL_ROOT = "/";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGINED = "logined";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String INDEX_HTML = "/index.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";

    public static Response createUser(final Request request) {
        final QueryParameter queries = request.getQueryParameters();
        final String userId = queries.getValue(USER_ID);
        final String password = queries.getValue(PASSWORD);
        final String name = queries.getValue(NAME);
        final String email = queries.getValue(EMAIL);
        final User user = new User(userId, password, name, email);

        Database.addUser(user);
        LOG.debug(user.toString());

        return new Response.Builder().redirect(URL_ROOT).build();
    }

    public static Response login(final Request request) {
        final Cookie cookie = request.getCookie();
        final QueryParameter queries = request.getQueryParameters();
        final User tryingUser = new User(queries.getValue(USER_ID), queries.getValue(PASSWORD));
        final User existUser = Database.findUserById(queries.getValue(USER_ID));
        final Response.Builder response = new Response.Builder();
        cookie.set(LOGINED, FALSE);
        response.redirect(LOGIN_FAILED_HTML);
        if (tryingUser.equals(existUser)) {
            cookie.set(LOGINED, TRUE);
            response.redirect(INDEX_HTML);
        }
        response.setCookie(cookie);
        return response.build();
    }
}
