package controller;

import http.request.Request;
import http.response.Response;
import http.response.ResponseFactory;
import service.UserService;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = UserService.getInstance();
    }

    public static UserController getInstance() {
        return UserControllerHolder.INSTANCE;
    }

    public Response userForm(Request request) {
        return ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/templates/");
    }

    public Response createUser(Request request) {
        userService.createUser(request.getParams());
        return ResponseFactory.getResponse(request.getRequestPath().getPath(), "redirect://../resources/templates/");
    }

    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController();
    }
}
