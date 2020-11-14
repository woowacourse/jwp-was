package application.controller;

import application.dto.HttpRequestToDtoConverter;
import application.service.UserService;
import com.github.jknack.handlebars.Template;
import controller.AbstractController;
import controller.WrongRequestException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;
import resource.ContentType;
import response.Cookies;
import response.HttpResponse;
import response.StatusCode;
import utils.TemplateBuilder;

public class UserController extends AbstractController {

    public static final String CREATE_URI_PATH = "/user/create";
    public static final String LOGIN_URI_PATH = "/user/login";
    public static final String FIND_USER_URI_PATH = "/user/list";

    private final UserService userService = new UserService();

    private HttpResponse create(HttpRequest request) {
        userService.create(HttpRequestToDtoConverter.toUserCreateRequest(request));

        return new HttpResponse(StatusCode.FOUND, "/");
    }

    private HttpResponse login(HttpRequest request) {
        boolean isRightUser = userService.isExistUser(
            HttpRequestToDtoConverter.toLoginRequest(request));

        if (isRightUser) {
            return new HttpResponse(StatusCode.FOUND, "/")
                .setCookies(Cookies.createWithSingleCookie("login", "true", "/"));
        }
        return new HttpResponse(StatusCode.FOUND, "/user/login_failed.html")
            .setCookies(Cookies.createWithSingleCookie("login", "false", "/"));
    }

    private HttpResponse findAllUsers(HttpRequest httpRequest) {
        Map<String, Object> handlebarData = new HashMap<>();
        try {
            handlebarData.put("users", userService.findAllUsers());

            Template template = TemplateBuilder.build(httpRequest.getUriPath());
            String usersPage = template.apply(handlebarData);

            return new HttpResponse(StatusCode.OK, usersPage, ContentType.HTML)
                .setCookies(Cookies.createWithSingleCookie("login", "true", "/"));
        } catch (IOException e) {
            return new HttpResponse(StatusCode.INTERNAL_SERVER_ERROR);
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

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        if (httpRequest.getUriPath().equals(FIND_USER_URI_PATH)) {
            return findAllUsers(httpRequest);
        }
        throw new WrongRequestException("uri that does not exist in the POST method.");
    }
}
