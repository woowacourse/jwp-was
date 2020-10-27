package controller;

import application.AbstractController;
import db.DataBase;
import domain.model.User;
import servlet.HttpRequest;
import servlet.HttpResponse;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User newUser = User.builder()
            .userId(httpRequest.getParameter("userId"))
            .password(httpRequest.getParameter("password"))
            .name(httpRequest.getParameter("name"))
            .email(httpRequest.getParameter("email")).build();

        DataBase.addUser(newUser);
        httpResponse.sendRedirect("/index.html");
    }
}
