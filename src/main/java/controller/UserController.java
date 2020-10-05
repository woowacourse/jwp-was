package controller;

import domain.user.service.UserService;
import webserver.EntityHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.createUser(httpRequest);
        httpResponse.addHeader(EntityHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        httpResponse.forward("./templates/index.html");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.createUser(httpRequest);
        httpResponse.sendRedirect("/index.html");
    }
}
