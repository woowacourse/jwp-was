package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.UserFactory;

public class UserCreateController extends AbstractController {
    public static final String URL = "/user/create";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        addUser(request, response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        addUser(request, response);
    }

    private void addUser(HttpRequest request, HttpResponse response) {
        DataBase.addUser(UserFactory.createUser(request));
        response.sendRedirect("/index.html");
    }
}
