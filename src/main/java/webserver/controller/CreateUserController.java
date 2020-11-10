package webserver.controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        DataBase.addUser(new User(
            httpRequest.getBodyParameter("userId"),
            httpRequest.getBodyParameter("password"),
            httpRequest.getBodyParameter("name"),
            httpRequest.getBodyParameter("email")
        ));
        httpResponse.sendRedirect("/index.html");
    }
}
