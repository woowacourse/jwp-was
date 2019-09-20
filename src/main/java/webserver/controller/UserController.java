package webserver.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if(request.hasParameters()) {
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            response.response200Header(0);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        super.doPost(request, response);
    }
}
