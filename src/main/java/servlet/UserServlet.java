package servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import static model.User.*;

public class UserServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse response) {
        User user = new User(
                httpRequest.getParam(USER_ID_KEY),
                httpRequest.getParam(USER_PASSWORD_KEY),
                httpRequest.getParam(USER_NAME_KEY),
                httpRequest.getParam(USER_EMAIL_KEY));
        DataBase.addUser(user);

        response.redirect("/index.html");
    }
}
