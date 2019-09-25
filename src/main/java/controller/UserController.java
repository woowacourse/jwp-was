package controller;

import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import http.response.ResponseHeaders;
import http.response.ResponseStatus;
import service.UserService;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class UserController implements Controller {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.POST, RequestMethod.GET);
    private List<String> allowedUrlPaths = Arrays.asList("/user/create", "/user/list");

    private UserService userService = UserService.getInstance();

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return (isAllowedMethod(controllerMapper.getRequestMethod())
                && isAllowedUrlPath(controllerMapper.getOriginalUrlPath()));
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        if (request.getUrl().getOriginalUrlPath().equals("/user/create")) {
            createUserAndRedirect(request, response);
        }
        if (request.getUrl().getOriginalUrlPath().equals("/user/list")) {
            getUserList(request, response);
        }
    }

    private void getUserList(Request request, Response response) throws IOException, URISyntaxException {
        if (request.getRequestInformation().getParameter("Cookie:") != null && request.getRequestInformation().getParameter("Cookie:").equals("logined=true")) {
            response.setResponseStatus(ResponseStatus.OK);
            response.setResponseHeaders(new ResponseHeaders());
            response.addResponseHeaders("Content-Type: ", "text/html");
            byte[] body = FileIoUtils.loadFileFromClasspath("../resources/templates/user/list.html");
            response.setResponseBody(body);
        }

        if (request.getRequestInformation().getParameter("Cookie:") == null || !request.getRequestInformation().getParameter("Cookie:").equals("logined=true")) {
            response.setResponseStatus(ResponseStatus.FOUND);
            response.setResponseHeaders(new ResponseHeaders());
            response.setEmptyResponseBody();
            response.addResponseHeaders("Location: ", "http://localhost:8080/user/login.html");
        }
    }

    private void createUserAndRedirect(Request request, Response response) {
        userService.createUser(request.getQueryParameters().getQueryParameters());
        response.setResponseStatus(ResponseStatus.FOUND);
        response.setResponseHeaders(new ResponseHeaders());
        response.setEmptyResponseBody();
        response.addResponseHeaders("Location: ", "http://localhost:8080/index.html");
    }

    private boolean isAllowedUrlPath(String originalUrlPath) {
        return allowedUrlPaths.stream()
                .anyMatch(originalUrlPath::contains);
    }

    private boolean isAllowedMethod(RequestMethod requestMethod) {
        return allowedMethods.stream()
                .anyMatch(method -> method == requestMethod);
    }
}
