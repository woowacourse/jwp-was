package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        save(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));

        httpResponse.sendRedirect("index.html");
    }

    private void save(String userId, String password, String name, String email) {
        if (DataBase.findUserById(userId) == null) {
            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            return;
        }

        throw new IllegalArgumentException();
    }
}
