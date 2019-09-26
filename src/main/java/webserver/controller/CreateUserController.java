package webserver.controller;

import http.request.Request;
import http.response.Response;
import model.UserService;

public class CreateUserController extends HttpController {

    private CreateUserController() {
    }

    public static CreateUserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doGet(Request request, Response response) {
        String location = new UserService().addUser(request.extractQueryParameter());
        response.redirect(location);
    }

    @Override
    protected void doPost(Request request, Response response) {
        String location = new UserService().addUser(request.extractFormData());
        response.redirect(location);
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }
}
