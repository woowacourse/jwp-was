package controller.methods;

import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import service.UserService;

public class CreateUserMethod implements ControllerMethod {
    private UserService userService;

    public CreateUserMethod(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isMapping(Request request) {
        return (RequestMethod.POST == request.getRequestMethod()
                &&
                "/user/create".equals(request.getUrl().getOriginalUrlPath()));
    }

    @Override
    public void processResponse(Request request, Response response) {
        userService.createUser(request.getQueryParameters().getQueryParameters());
        response.found()
                .putResponseHeaders("Location: ", "http://localhost:8080/index.html");
    }
}
