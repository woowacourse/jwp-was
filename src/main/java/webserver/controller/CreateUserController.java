package webserver.controller;

import http.Request;
import http.Response;
import model.UserService;

public class CreateUserController extends HttpController {
    private static final String HTML = "html";
    private static final int REDIRECT_STATUS_CODE = 302;

    private CreateUserController() {
    }

    public static CreateUserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }

    @Override
    protected void doPost(Request request, Response response) {
        String location = new UserService().addUser(request.extractFormData());

        response.setLocation(location);
        response.setContentType(HTML);
        response.setStatusCode(REDIRECT_STATUS_CODE);
    }
}
