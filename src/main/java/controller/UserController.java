package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController extends AbstractController {

    private UserService userService = UserService.getInstance();

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.createRequestBody(httpRequest);
        httpResponse.response302Header(Integer.parseInt(httpRequest.getHeaderByName("Content-Length")));
    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.createRequestParams(httpRequest);
        httpResponse.response302Header(Integer.parseInt(httpRequest.getHeaderByName("Content-Length")));
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.createRequestBody(httpRequest);
        httpResponse.response302Header(Integer.parseInt(httpRequest.getHeaderByName("Content-Length")));
    }
}
