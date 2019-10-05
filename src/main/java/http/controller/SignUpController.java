package http.controller;

import db.DataBase;
import http.model.HttpRequest;
import http.model.HttpResponse;
import model.User;

public class SignUpController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        User user = new User(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));

        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .sendRedirect("/index.html")
                .build();
    }
}