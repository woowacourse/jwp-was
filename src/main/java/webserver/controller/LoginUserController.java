package webserver.controller;

import http.HttpResponse;
import http.request.HttpRequest;
import model.UserController;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginUserController extends HttpController {

    //@TODO 리팩토링
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        UserController userController = new UserController();
        String path = userController.login(httpRequest);
        int index = path.indexOf("redirect:") + "redirect".length();
        String redirectPath = path.substring(index + 1);

        if (path.contains("redirect:")) {
            httpResponse.addHeader("Location", redirectPath);
            httpResponse.addHeader("Content-Type", "text/html");
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
            httpResponse.sendRedirect();
            return;
        }

        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        httpResponse.forward(PathHandler.path(path));
    }
}
