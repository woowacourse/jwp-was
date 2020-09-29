package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController extends AbstractController {

    private final UserService userService = UserService.getInstance();

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.createRequestParams(httpRequest);
        httpResponse.response302Header(Integer.parseInt(httpRequest.getHttpRequestHeaderByName("Content-Length")));
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.createRequestBody(httpRequest);
        httpResponse.response302Header(Integer.parseInt(httpRequest.getHttpRequestHeaderByName("Content-Length")));
    }
}
