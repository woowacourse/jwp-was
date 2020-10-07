package controller;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController extends AbstractController {

    private static final String CONTENT_LENGTH = "Content-Length";

    private final UserService userService = UserService.getInstance();

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        userService.createRequestParams(httpRequest);
        httpResponse.response302(Integer.parseInt(httpRequest.getHttpRequestHeaderByName(CONTENT_LENGTH)));
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        userService.createRequestBody(httpRequest);
        httpResponse.response302(Integer.parseInt(httpRequest.getHttpRequestHeaderByName(CONTENT_LENGTH)));
    }
}
