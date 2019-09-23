package webserver.controller;

import http.request.Request;
import http.response.Response;
import model.UserService;

public class CreateUserController extends HttpController {
    private static final String FOUND = "FOUND";
    private static final int REDIRECT_STATUS_CODE = 302;

    private CreateUserController() {
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }

    public static CreateUserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doGet(Request request, Response response) {
        String location = new UserService().addUser(request.extractQueryParameter());

        setResponse(response, location);
    }


    @Override
    protected void doPost(Request request, Response response) {
        String location = new UserService().addUser(request.extractFormData());

        setResponse(response, location);
    }

    private void setResponse(Response response, String location) {
        response.setStatusCode(REDIRECT_STATUS_CODE);
        response.setReasonPhrase(FOUND);
        response.setLocation(location);
    }
}
