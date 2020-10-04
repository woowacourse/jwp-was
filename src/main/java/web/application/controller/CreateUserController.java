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
        User newUser = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"),
            httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(newUser);
        httpResponse.sendRedirect("/index.html");
    }

    private static class ControllerCache {
        private static final CreateUserController CREATE_USER_CONTROLLER = new CreateUserController();
    }
}
