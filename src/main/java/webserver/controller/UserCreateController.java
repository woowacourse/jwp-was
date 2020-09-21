package webserver.controller;

import java.io.IOException;

import db.DataBase;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserCreateController extends Controller {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        User user = new User(
            request.getParam("userId"),
            request.getParam("password"),
            request.getParam("name"),
            request.getParam("email")
        );
        DataBase.addUser(user);
        response.status(HttpStatus.CREATED).end(null);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }
}
