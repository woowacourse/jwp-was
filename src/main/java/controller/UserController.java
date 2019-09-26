package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import webserver.domain.Request;
import webserver.domain.Response;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class UserController {
    private static final Logger LOG = getLogger(UserController.class);
    private static final String URL_ROOT = "/";

    public static Response createUser(final Request request) {
        final Map<String, String> queries = request.getQueryParameters();
        final String userId = queries.get("userId");
        final String password = queries.get("password");
        final String name = queries.get("name");
        final String email = queries.get("email");
        final User user = new User(userId, password, name, email);

        DataBase.addUser(user);
        LOG.debug(user.toString());

        return new Response.Builder().redirectUrl(URL_ROOT).build();
    }
}
