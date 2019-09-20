package controller;

import http.request.Request;
import http.response.RedirectResponse;
import http.response.Response;
import http.response.ResponseFactory;
import service.UserService;

public class PostDataController implements Controller {
    private UserService userService;
    private Request request;

    public PostDataController(Request request) {
        this.request = request;
        userService = UserService.getInstance();
    }

    public void createUser(Request request) {
        userService.createUser(request.getParams());
        ResponseFactory.getResponse(request);
    }

    @Override
    public Response createResponse() {
        createUser(request);
        return new RedirectResponse();
    }
}
