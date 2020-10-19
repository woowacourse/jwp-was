package domain.user.web;

import java.io.IOException;

import controller.AbstractController;
import domain.user.model.User;
import domain.user.service.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class UserCreateController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.containsAll(User.USER_ID, User.PASSWORD, User.NAME, User.EMAIL)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.error();
        }
        if (httpRequest.containsAll(User.USER_ID, User.PASSWORD, User.NAME, User.EMAIL)) {
            User user = new User(httpRequest.getParameter(User.USER_ID), httpRequest.getParameter(User.PASSWORD),
                httpRequest.getParameter(User.NAME), httpRequest.getParameter(User.EMAIL));
            UserService.addUser(user);
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpResponse.sendRedirect("/index.html");
        }
    }
}
