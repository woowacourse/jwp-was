package controller;

import db.DataBase;
import domain.model.User;

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
