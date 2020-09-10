package controller;

import http.request.Request;
import http.response.Response;
import service.UserService;

public class UserController implements Controller {

    private UserService userService = UserService.getInstance();

    @Override
    public void run(Request request, Response response) {
        userService.create(request);
        response.response302Header(Integer.parseInt(request.getHeader("Content-Length")));
    }
}
