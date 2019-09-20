package webserver.controller;

import http.Request;
import http.Response;
import model.UserService;

public class CreateUserController extends HttpController {
    private static final String HTML = "html";
    public static final int REDIRECT_STATUS_CODE = 302;

    @Override
    protected void doPost(Request request, Response response) {
        String location = new UserService().addUser(request.extractFormData());

        response.setLocation(location);
        response.setType(HTML);
        response.setStatusCode(REDIRECT_STATUS_CODE);
    }
}
