package web.application.controller;

import db.DataBase;
import web.application.domain.model.User;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

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
