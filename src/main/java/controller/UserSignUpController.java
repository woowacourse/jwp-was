package controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.FoundResponseMetaData;
import webserver.response.HttpResponse;
import webserver.response.ResponseMetaData;

import java.io.IOException;

public class UserSignUpController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = new FoundResponseMetaData(request, "/index.html");
        response.setResponseMetaData(responseMetaData);

        doPost(request, response);
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.findRequestBodyParam("userId");
        String password = request.findRequestBodyParam("password");
        String name = request.findRequestBodyParam("name");
        String email = request.findRequestBodyParam("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        response.makeResponse();
    }
}
