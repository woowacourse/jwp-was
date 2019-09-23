package webserver.controller;

import http.HttpRequest;
import http.Response;
import model.UserController;

public class CreateUserController extends HttpController {
    private static final String HTML = "html";
    public static final int REDIRECT_STATUS_CODE = 302;

    @Override
    protected void doPost(HttpRequest httpRequest, Response response) {
        String location = new UserController().addUser(httpRequest);

        response.setLocation(location);
        response.setType(HTML);
        response.setStatusCode(REDIRECT_STATUS_CODE);
    }
}
