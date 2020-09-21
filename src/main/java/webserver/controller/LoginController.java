package webserver.controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class LoginController extends AbstractController{

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String userId = httpRequest.getBodyParameter("userId");
        String password = httpRequest.getBodyParameter("password");
        User user = DataBase.findUserById(userId);

        if(user.isSamePassword(password)) {
            httpResponse.sendRedirect("/index.html");
            return;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
