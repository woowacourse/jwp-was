package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class UserController extends AbstractController {
    @Override
    protected void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");
        String name = httpRequest.getBodyValue("name");
        String email = httpRequest.getBodyValue("email");
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        httpResponse.sendRedirect("/index.html");
        httpResponse.noContent();
    }
}
