package controller;

import controller.controllermapper.ControllerMapper;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateUserController implements Controller {
    private final RequestMapping requestMapping = new RequestMapping(RequestMethod.POST, "/user/create");
    private UserService userService = UserService.getInstance();


    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return requestMapping.isCorrectMapping(controllerMapper);
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        userService.createUser(request.getQueryParameters().getQueryParameters());
        response.found()
                .putResponseHeaders("Location: ", "http://localhost:8080/index.html");
    }
}
