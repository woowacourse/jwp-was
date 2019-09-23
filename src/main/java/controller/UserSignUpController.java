package controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserSignUpController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        response.setHttpStatus(HttpStatus.FOUND);
        response.setLocation("/index.html");

        doPost(request, response);
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        String userId = request.findRequestBodyParam("userId");
        String password = request.findRequestBodyParam("password");
        String name = request.findRequestBodyParam("name");
        String email = request.findRequestBodyParam("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        response.makeResponse(request);
    }
}
