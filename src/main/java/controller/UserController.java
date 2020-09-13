package controller;

import static com.google.common.net.HttpHeaders.LOCATION;

import java.util.HashMap;
import java.util.Map;
import model.User;
import service.UserService;
import service.dto.UserRequest;
import service.dto.UserRequestAssembler;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

public class UserController {

    private final ControllerAdvice controllerAdvice;
    private final UserService userService;

    public UserController(ControllerAdvice controllerAdvice, UserService userService) {
        this.controllerAdvice = controllerAdvice;
        this.userService = userService;
    }

    public HttpResponse createUser(HttpRequest httpRequest) {
        try {
            UserRequest userRequest = UserRequestAssembler.assemble(httpRequest.getParameters());
            User user = userService.createUser(userRequest);
            Map<String, String> headers = new HashMap<>();
            headers.put(LOCATION, "/users/" + user.getUserId());
            return HttpResponse.of(httpRequest.getProtocol(), HttpStatusCode.CREATED, headers, "");
        } catch (Exception e) {
            return controllerAdvice.handleException(httpRequest, e);
        }
    }
}
