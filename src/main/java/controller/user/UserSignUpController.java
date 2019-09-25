package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseMetaData;
import webserver.response.ResponseMetaDataGenerator;

import java.io.IOException;

public class UserSignUpController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = ResponseMetaDataGenerator.buildDefaultFoundMetaData(request, "/index.html");
        response.setResponseMetaData(responseMetaData);

        doPost(request, response);
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        User user = createUser(request);

        DataBase.addUser(user);

        response.makeResponse();
    }

    private User createUser(final HttpRequest request) {
        String userId = request.findRequestBodyParam("userId");
        String password = request.findRequestBodyParam("password");
        String name = request.findRequestBodyParam("name");
        String email = request.findRequestBodyParam("email");

        return new User(userId, password, name, email);
    }
}
