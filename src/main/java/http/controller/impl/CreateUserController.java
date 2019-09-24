package http.controller.impl;

import db.DataBase;
import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    public static final String URL = "/user/create";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        addUser(request, response);
    }

    private void addUser(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        DataBase.addUser(new User(userId, password, name, email));
        response.redirect("/index.html");
    }
}
