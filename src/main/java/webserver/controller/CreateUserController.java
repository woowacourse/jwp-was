package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;
import model.UserController;

import java.io.IOException;

public class CreateUserController extends HttpController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String location = new UserController().addUser(httpRequest);

        httpResponse.addHeader("Location", location);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.sendRedirect();
    }
}
