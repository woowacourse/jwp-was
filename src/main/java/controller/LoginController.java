package controller;

import controller.exception.NotFoundUserIdException;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import view.RedirectView;
import view.View;

public class LoginController extends AbstractController {
    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = DataBase.findUserById(httpRequest.getRequestBody("userId"))
                .orElseThrow(NotFoundUserIdException::new);

        user.matchPassword(httpRequest.getRequestBody("password"));
        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");

        return new RedirectView("index.html");
    }
}