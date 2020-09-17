package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.StatusCode;

public class UserController extends AbstractController {

    private HttpResponse createUser(HttpRequest request) {
        User user = new User(
            request.getValueFromFormData("userId"),
            request.getValueFromFormData("password"),
            request.getValueFromFormData("name"),
            request.getValueFromFormData("email")
        );
        DataBase.addUser(user);

        return new HttpResponse(StatusCode.FOUND, "/");
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        return createUser(httpRequest);
    }

    @Override
    public HttpResponse doPut(HttpRequest httpRequest) {
        return null;
    }

    @Override
    public HttpResponse doDelete(HttpRequest httpRequest) {
        return null;
    }
}
