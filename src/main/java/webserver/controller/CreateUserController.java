package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;
import model.UserController;

public class CreateUserController extends HttpController {
    private static final String HTML = "html";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String location = new UserController().addUser(httpRequest);

        httpResponse.setLocation(location);
        httpResponse.setType(HTML);
        httpResponse.setStatusCode(REDIRECT_STATUS_CODE);
    }
}
