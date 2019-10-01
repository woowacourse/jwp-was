package http.controller;

import db.DataBase;
import exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.redirect("/index.html");
    }
}
