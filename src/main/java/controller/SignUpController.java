package controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import static controller.support.Constants.*;

public class SignUpController extends AbstractController {

    @Override
    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getParameter(USER_ID),
                httpRequest.getParameter(USER_PASSWORD),
                httpRequest.getParameter(USER_NAME),
                httpRequest.getParameter(USER_EMAIL));
        DataBase.addUser(user);
        return REDIRECT_PREFIX + "/index.html";
    }
}
