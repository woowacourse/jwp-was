package application.controller;

import application.dto.HttpRequestToDtoConverter;
import application.filter.auth.Authority;
import application.filter.auth.UnauthorizedException;
import application.service.UserService;
import com.github.jknack.handlebars.Template;
import controller.AbstractController;
import controller.WrongRequestException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;
import resource.ContentType;
import response.HttpResponse;
import response.ResponseCookies;
import response.StatusCode;
import session.Session;
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

    private HttpResponse login(HttpRequest request, Session session) {
        boolean isRightUser = userService.isExistUser(
            HttpRequestToDtoConverter.toLoginRequest(request));

        if (isRightUser) {
            session.setAttribute("login", true);

            return new HttpResponse(StatusCode.FOUND, "/").setCookies(
                ResponseCookies.createWithSingleCookie("sessionId", session.getId(), "/"));
        }
        return new HttpResponse(StatusCode.FOUND, "/user/login_failed.html");
    }

    private HttpResponse findAllUsers(HttpRequest httpRequest, Session session) {
        try {
            Authority.validateAuthority(session);

            return makeResponseForFindAllUsers(httpRequest);
        } catch (IOException e) {
            return new HttpResponse(StatusCode.INTERNAL_SERVER_ERROR);
        } catch (UnauthorizedException e) {
            return new HttpResponse(StatusCode.FOUND, "login.html");
        }
    }

    private HttpResponse makeResponseForFindAllUsers(HttpRequest httpRequest) throws IOException {
        Map<String, Object> handlebarData = new HashMap<>();
        handlebarData.put("users", userService.findAllUsers());

        Template template = TemplateBuilder.build(httpRequest.getUriPath());
        String usersPage = template.apply(handlebarData);

        return new HttpResponse(StatusCode.OK, usersPage, ContentType.HTML)
            .setCookies(ResponseCookies.createWithSingleCookie("login", "true", "/"));
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest, Session session) {
        if (httpRequest.getUriPath().equals(CREATE_URI_PATH)) {
            return create(httpRequest);
        }
        if (httpRequest.getUriPath().equals(LOGIN_URI_PATH)) {
            return login(httpRequest, session);
        }
        throw new WrongRequestException("uri that does not exist in the POST method.");
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest, Session session) {
        String uriPath = httpRequest.getUriPath();

        if (uriPath.equals(FIND_USER_URI_PATH)) {
            return findAllUsers(httpRequest, session);
        }
        throw new WrongRequestException("uri that does not exist in the POST method.");
    }
}
