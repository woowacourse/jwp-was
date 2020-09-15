package webserver.controller;

import model.User;
import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public class CreateUserController extends AbstractController {

    private CreateUserController() {
    }

    public static CreateUserController getInstance() {
        return ControllerCache.createUserController;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User newUser = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"),
            httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        httpResponse.sendRedirect("/index.html");
    }

    private static class ControllerCache {
        private static final CreateUserController createUserController = new CreateUserController();
    }
}
