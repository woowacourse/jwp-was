package servlet.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import domain.UserService;
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
            httpResponse.addCookie("logined", "true");
            httpResponse.addCookie("Path", "/");
            httpResponse.sendRedirect();
            return;
        }
        httpResponse.addCookie("logined", "false");
        httpResponse.addCookie("Path", "/");
        httpResponse.forward(ViewResolver.resolve("/user/login_failed.html"));
    }
}
