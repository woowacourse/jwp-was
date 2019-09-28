package servlet.controller;

import domain.UserService;
import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.resolver.UserResolver;
import servlet.view.ViewResolver;

import java.io.IOException;

public class UserLoginController extends HttpController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        UserService userService = new UserService();
        httpResponse.addHeader("Content-Type", "text/html");

        if (userService.login(UserResolver.resolve(httpRequest))) {
            httpResponse.addHeader("Location", "/index.html");
            httpRequest.addSessionAttribute("logined", "true");
            httpResponse.addCookie("Path", "/");
            httpResponse.sendRedirect();
            return;
        }
        httpRequest.addSessionAttribute("logined", "false");
        httpResponse.addCookie("Path", "/");
        httpResponse.forward(ViewResolver.resolve("/user/login_failed.html"));
    }
}
