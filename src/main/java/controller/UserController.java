package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

public class UserController implements Controller {

    private UserService userService = UserService.getInstance();

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.create(httpRequest);
        httpResponse.response302Header(Integer.parseInt(httpRequest.getHeaderByName("Content-Length")));
    }
}
