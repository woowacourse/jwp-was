package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import webserver.message.request.Request;
import webserver.message.request.RequestBody;
import webserver.message.response.Response;

import static org.slf4j.LoggerFactory.getLogger;

public class UserController extends AbstractController {
    private static final Logger LOG = getLogger(UserController.class);

    protected Response doPost(final Request request) {
        final RequestBody body = request.getBody();
        final String userId = body.getQueryValue("userId");
        final String password = body.getQueryValue("password");
        final String name = body.getQueryValue("name");
        final String email = body.getQueryValue("email");
        final User user = new User(userId, password, name, email);

        DataBase.addUser(user);
        LOG.debug(user.toString());

        return new Response.Builder().redirectUrl("/").build();
    }
}
