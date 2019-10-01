package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends DefaultController {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        DataBase.addUser(createUser(httpRequest));

        httpResponse.redirect("/index.html");
    }

    private User createUser(HttpRequest httpRequest) {
        return new User(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));
    }
}
