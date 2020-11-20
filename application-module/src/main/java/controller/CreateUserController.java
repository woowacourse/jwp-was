package controller;

import db.DataBase;
import domain.controller.AbstractController;
import domain.model.User;
import domain.request.HttpRequest;
import domain.response.HttpResponse;

public class CreateUserController extends AbstractController {

    private CreateUserController() {
        super();
    }

    public static CreateUserController getInstance() {
        return ControllerCache.CREATE_USER_CONTROLLER;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User newUser = User.builder()
            .userId(httpRequest.getParameter("userId"))
            .password(httpRequest.getParameter("password"))
            .name(httpRequest.getParameter("name"))
            .email(httpRequest.getParameter("email")).build();

        DataBase.addUser(newUser);
        httpResponse.sendRedirect("/index.html");
    }

    private static class ControllerCache {

        private static final CreateUserController CREATE_USER_CONTROLLER = new CreateUserController();

    }
}
