package controller;

import domain.user.service.UserService;
import webserver.ContentType;
import webserver.EntityHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserCreateController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.createUser(httpRequest);
        httpResponse.addHeader(EntityHeader.CONTENT_TYPE, ContentType.HTML.get());
        httpResponse.forward("./templates/index.html");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.createUser(httpRequest);
        httpResponse.sendRedirect("/index.html");
    }
}
