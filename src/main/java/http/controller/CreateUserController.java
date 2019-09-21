package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getEntityValue("userId"),
                httpRequest.getEntityValue("password"),
                httpRequest.getEntityValue("name"),
                httpRequest.getEntityValue("email"));
        DataBase.addUser(user);
        httpResponse.redirect("/index.html");
    }
}
