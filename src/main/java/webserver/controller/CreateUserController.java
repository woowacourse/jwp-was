package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;
import model.UserController;

public class CreateUserController extends HttpController {
    private static final String HTML = "html";
    public static final int REDIRECT_STATUS_CODE = 302;

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String location = new UserController().addUser(httpRequest);

        httpResponse.setLocation(location);
        httpResponse.setType(HTML);
        httpResponse.setStatusCode(REDIRECT_STATUS_CODE);
    }
}
