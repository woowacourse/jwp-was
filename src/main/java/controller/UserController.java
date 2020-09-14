package controller;

import static com.google.common.net.HttpHeaders.LOCATION;

import controller.annotation.Controller;
import controller.annotation.RequestMapping;
import controller.dto.UserRequest;
import controller.dto.UserRequestAssembler;
import java.util.HashMap;
import java.util.Map;
import service.UserService;
import webserver.HttpMethod;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

@Controller
public class UserController {

    private static final String INDEX_HTML = "/index.html";

    private final ControllerAdvice controllerAdvice;
    private final UserService userService;

    public UserController(ControllerAdvice controllerAdvice, UserService userService) {
        this.controllerAdvice = controllerAdvice;
        this.userService = userService;
    }

    @RequestMapping(method = HttpMethod.POST, urlPath = "/user/create")
    public HttpResponse createUser(HttpRequest httpRequest) {
        UserRequest userRequest = UserRequestAssembler.assemble(httpRequest.getParameters());
        userService.createUser(userRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put(LOCATION, INDEX_HTML);
        return HttpResponse.of(httpRequest.getProtocol(), HttpStatusCode.FOUND, headers, "");
    }
}
