package servlet.controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import model.UserService;
import servlet.resolver.UserResolver;
import servlet.view.ViewResolver;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserLoginController extends HttpController {

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        UserService userService = new UserService();

        if (userService.login(UserResolver.resolve(httpRequest))) {
            httpResponse.addHeader("Location", "/index.html");
            httpResponse.addHeader("Content-Type", "text/html");
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
            httpResponse.sendRedirect();
            return;
        }
        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        httpResponse.forward(ViewResolver.resolve("/user/login_failed.html"));
    }
}
