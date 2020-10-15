package application.controller;

import application.service.UserService;
import controller.AbstractController;
import controller.WrongRequestException;
import request.HttpRequest;
import response.Cookies;
import response.HttpResponse;
import response.StatusCode;

public class UserController extends AbstractController {

    public static final String CREATE_URI_PATH = "/user/create";
    public static final String LOGIN_URI_PATH = "/user/login";

    private final UserService userService = new UserService();

    private HttpResponse create(HttpRequest request) {
        userService.create(request);

        return new HttpResponse(StatusCode.FOUND, "/");
    }

    private HttpResponse login(HttpRequest request) {
        try {
            userService.login(request);
            return new HttpResponse(StatusCode.FOUND, "/")
                .setCookies(Cookies.createWithSingleCookie("login", "true"));
        } catch(WrongUserIdPasswordException e) {
            return new HttpResponse(StatusCode.FOUND, "/user/login_failed.html")
                .setCookies(Cookies.createWithSingleCookie("login", "false"));
        }
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        if (httpRequest.getUriPath().equals(CREATE_URI_PATH)) {
            return create(httpRequest);
        }
        if (httpRequest.getUriPath().equals(LOGIN_URI_PATH)) {
            return login(httpRequest);
        }
        throw new WrongRequestException("uri that does not exist in the POST method.");
    }
}
