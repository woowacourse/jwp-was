package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
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

    public static Response createUser(final Request request) {
        final QueryParameter queries = request.getQueryParameters();
        final String userId = queries.getValue(USER_ID);
        final String password = queries.getValue(PASSWORD);
        final String name = queries.getValue(NAME);
        final String email = queries.getValue(EMAIL);
        final User user = new User(userId, password, name, email);

        Database.addUser(user);
        LOG.debug(user.toString());

        return new Response.Builder().redirectUrl(URL_ROOT).build();
    }
}
