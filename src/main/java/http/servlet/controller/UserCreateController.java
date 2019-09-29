package http.servlet.controller;

import domain.UserService;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.servlet.resolver.UserResolver;

import java.io.IOException;

public class UserCreateController extends HttpController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        new UserService().addUser(UserResolver.resolve(httpRequest));

        httpResponse.addHeader("Location", "/index.html");
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.sendRedirect();
    }
}
