package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.StatusCode;

public class UserController {

    public HttpResponse createUser(HttpRequest request) {
        User user = new User(
            request.getValueFromFormData("userId"),
            request.getValueFromFormData("password"),
            request.getValueFromFormData("name"),
            request.getValueFromFormData("email")
        );
        DataBase.addUser(user);

        return new HttpResponse(StatusCode.FOUND, "/");
    }
}
