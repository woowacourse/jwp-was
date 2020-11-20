package controller;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController extends AbstractController {

    private static final String REDIRECT_HOME = "/index.html";

    private final UserService userService = UserService.getInstance();

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        userService.createRequestParams(httpRequest);
        httpResponse.response302(REDIRECT_HOME);
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        userService.createRequestBody(httpRequest);
        httpResponse.response302(REDIRECT_HOME);
    }
}
