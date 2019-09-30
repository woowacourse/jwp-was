package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import view.RedirectView;
import view.View;

public class CreateUserController extends AbstractController {
    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getRequestBody("userId"),
                httpRequest.getRequestBody("password"),
                httpRequest.getRequestBody("name"),
                httpRequest.getRequestBody("email"));
        DataBase.addUser(user);

        return new RedirectView("index.html");
    }
}