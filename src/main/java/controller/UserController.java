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
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        return createUser(httpRequest);
    }

    @Override
    protected HttpResponse doPut(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    @Override
    protected HttpResponse doDelete(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.NOT_FOUND);
    }
}
