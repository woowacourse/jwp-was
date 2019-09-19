package controller;

import model.User;
import webserver.domain.QueryParameter;
import webserver.domain.Request;
import webserver.domain.Response;

public class UserController {

    public Response createUser(final Request request) {
        final QueryParameter queries = request.getQueryParameter();
        final String userId = queries.getValue("userId");
        final String password = queries.getValue("password");
        final String name = queries.getValue("name");
        final String email = queries.getValue("email");
        final User user = new User(userId, password, name, email);

        return new Response();
    }
}
