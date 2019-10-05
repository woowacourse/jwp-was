package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.RedirectView;
import view.View;

public class CreateUserController extends AbstractController {

    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getQueryParameter("userId"),
                httpRequest.getQueryParameter("password"),
                httpRequest.getQueryParameter("name"),
                httpRequest.getQueryParameter("email"));
        DataBase.addUser(user);

        return new RedirectView("index.html");
    }
}
