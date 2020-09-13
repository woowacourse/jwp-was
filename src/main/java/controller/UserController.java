package controller;

import static com.google.common.net.HttpHeaders.LOCATION;

import controller.dto.UserRequest;
import controller.dto.UserRequestAssembler;
import java.util.HashMap;
import java.util.Map;
import service.UserService;
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
            userService.createUser(userRequest);
            Map<String, String> headers = new HashMap<>();
            headers.put(LOCATION, "/index.html");
            return HttpResponse.of(httpRequest.getProtocol(), HttpStatusCode.FOUND, headers, "");
        } catch (Exception e) {
            return controllerAdvice.handleException(httpRequest, e);
        }
    }
}
