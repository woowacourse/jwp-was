package controller;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController extends AbstractController {

    private final UserService userService = UserService.getInstance();

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        userService.createRequestParams(httpRequest);
        httpResponse.response302();
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        userService.createRequestBody(httpRequest);
        httpResponse.response302();
    }
}
