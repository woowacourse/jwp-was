package controller;

import controller.methods.ControllerMethod;
import controller.methods.CreateUserMethod;
import controller.methods.GetUserListMethod;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class UserController implements Controller {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.POST, RequestMethod.GET);
    private List<String> allowedUrlPaths = Arrays.asList("/user/create", "/user/list");
    private UserService userService = UserService.getInstance();
    private List<ControllerMethod> userControllerMethods = Arrays.asList(new CreateUserMethod(userService), new GetUserListMethod());

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return (isAllowedMethod(controllerMapper.getRequestMethod())
                && isAllowedUrlPath(controllerMapper.getOriginalUrlPath()));
    }

    private boolean isAllowedUrlPath(String originalUrlPath) {
        return allowedUrlPaths.stream()
                .anyMatch(originalUrlPath::contains);
    }

    private boolean isAllowedMethod(RequestMethod requestMethod) {
        return allowedMethods.stream()
                .anyMatch(method -> method == requestMethod);
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        ControllerMethod controllerMethod = userControllerMethods.stream()
                .filter(method -> method.isMapping(request))
                .findAny()
                .orElseThrow(IllegalAccessError::new);

        controllerMethod.processResponse(request, response);
    }
}
