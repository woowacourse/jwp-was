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
    public static final String FIND_USER_URI_PATH = "/user/list";

    private final UserService userService = new UserService();

    private HttpResponse create(HttpRequest request) {
        userService.create(request);

        return new HttpResponse(StatusCode.FOUND, "/");
    }

    private HttpResponse login(HttpRequest request) {
        try {
            userService.login(request);
            return new HttpResponse(StatusCode.FOUND, "/")
                .setCookies(Cookies.createWithSingleCookie("login", "true", "/"));
        } catch(WrongUserIdPasswordException e) {
            return new HttpResponse(StatusCode.FOUND, "/user/login_failed.html")
                .setCookies(Cookies.createWithSingleCookie("login", "false", "/"));
        }
    }

    private HttpResponse findAllUsers(HttpRequest httpRequest) {
        return new HttpResponse(StatusCode.FOUND, "/user/list.html")
            .setCookies(Cookies.createWithSingleCookie("login", "true", "/"));
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

    @Override
    protected  HttpResponse doGet(HttpRequest httpRequest) {
        if (httpRequest.getUriPath().equals(FIND_USER_URI_PATH)) {
            return findAllUsers(httpRequest);
        }
        throw new WrongRequestException("uri that does not exist in the POST method.");
    }
}
