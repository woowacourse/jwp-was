package controller;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController extends AbstractController {

    private static final String REDIRECT_HOME = "http://localhost:8080/index.html";

    private final UserService userService = UserService.getInstance();

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        userService.createRequestParams(httpRequest);
        httpResponse.response302(REDIRECT_HOME);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        userService.createRequestBody(httpRequest);
        httpResponse.response302(REDIRECT_HOME);
    }
}
