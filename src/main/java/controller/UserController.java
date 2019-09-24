package controller;

import controller.core.Controller;
import http.request.HttpRequest;
import http.request.core.RequestMethod;
import http.request.core.RequestPath;
import service.UserService;

import java.util.Arrays;
import java.util.List;

public class UserController implements Controller {
    private final List<String> allowedMappers = Arrays.asList(
            RequestMethod.valueOf("GET").getMethod() + " ../resources/templates/user/form.html",
            RequestMethod.valueOf("GET").getMethod() + " ../resources/templates/user/create",
            RequestMethod.valueOf("POST").getMethod() + " ../resources/templates/user/create"
    );

    private UserService userService;
    private HttpRequest httpRequest;

    UserController() {
        userService = UserService.getInstance();
    }

    private void createPostUser() {
        userService.createUser(httpRequest.getData());
    }

    @Override
    public boolean isMapping(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        RequestMethod requestMethod = httpRequest.getRequestMethod();
        RequestPath requestPath = httpRequest.getRequestPath();
        String checkValue = requestMethod.getMethod() + " " + requestPath.getFullPath();

        return allowedMappers.stream()
                .anyMatch(checkValue::contains);
    }

    @Override
    public int doController() {
        try {
            createPostUser();
            return 302;
        } catch (Exception e) {
            return 500;
        }
    }

}
