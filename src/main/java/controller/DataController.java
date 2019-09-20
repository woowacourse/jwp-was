package controller;

import http.request.Request;
import http.response.RedirectResponse;
import http.response.Response;
import http.response.ResponseFactory;
import service.UserService;

public class DataController implements Controller {
    private UserService userService;
    private Request request;

    public DataController(Request request) {
        this.request = request;
        userService = UserService.getInstance();
    }

    public Response createUser(Request request) {
        userService.createUser(request.getParams());
        return ResponseFactory.getResponse(request);
    }

    @Override
    public Response createResponse() {
        createUser(request);
        return new RedirectResponse();
    }
}
