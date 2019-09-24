package controller;

import http.request.Request;
import http.request.RequestMethod;
import http.response.RedirectResponse;
import http.response.Response;
import service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements Controller {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.POST);
    private String allowedUrlPath = "/user/create";

    private UserService userService = UserService.getInstance();

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return (isAllowedMethod(controllerMapper.getRequestMethod())
                && isAllowedUrlPath(controllerMapper.getOriginalUrlPath()));
    }

    private boolean isAllowedUrlPath(String originalUrlPath) {
        return allowedUrlPath.contains(originalUrlPath);
    }

    private boolean isAllowedMethod(RequestMethod requestMethod) {
        return allowedMethods.stream()
                .anyMatch(method -> method == requestMethod);
    }

    @Override
    public Response createResponse(Request request) {
        return createPostResponse(request);
    }

    public Response createPostResponse(Request request) {
        Map<String, String> params = new HashMap<>();
        String queryParams = request.getRequestInformation().extractQueryParameters();
        String[] tokens = queryParams.split("&");
        Arrays.stream(tokens)
                .forEach(token -> {
                    String[] keyValues = token.split("=");
                    params.put(keyValues[0], keyValues[1]);
                });
        userService.createUser(params);
        return new RedirectResponse();
    }
}
