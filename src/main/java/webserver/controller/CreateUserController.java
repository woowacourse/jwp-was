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
        UserService.getInstance().addUser(request.extractQueryParameter());
        response.redirect("/index.html");
    }

    @Override
    protected void doPost(Request request, Response response) {
        UserService.getInstance().addUser(request.extractFormData());
        response.redirect("/index.html");
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }
}
