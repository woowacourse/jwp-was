package controller;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import exception.LoginFailException;
import exception.NoSessionException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.domain.User;
import model.general.ContentType;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import service.UserService;
import utils.HandlebarUtils;
import webserver.HttpSession;
import webserver.HttpSessions;

public class UserController extends AbstractController {

    private static final String USER_CREATE_REQUEST = "/user/create";
    private static final String LOGIN_REQUEST = "/user/login";
    private static final String LIST_REQUEST = "/user/list";
    private static final String PROFILE_REQUEST = "/user/profile";
    private static final String HOME_LOCATION = "/index.html";
    private static final String LOGIN_FAIL_REDIRECT_LOCATION = "/user/login_failed.html";
    private static final String LOGIN_REDIRECT_LOCATION = "/user/login.html";
    private static final String LOGIN_CHECK_COOKIE = "logined";
    private static final String USER_ATTRIBUTE_KEY = "user";
    private static final String LOGIN_SUCCESS_COOKIE_VALUE = "logined=true; Path=/";
    private static final String LOGIN_FAIL_COOKIE_VALUE = "logined=false; Path=/";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Method method = httpRequest.getMethod();

        if (method.equals(Method.GET)) {
            return doGet(httpRequest);
        }
        if (method.equals(Method.POST)) {
            return doPost(httpRequest);
        }

        return super.service(httpRequest);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        if (httpRequest.isSameUri(LIST_REQUEST)) {
            return showList(httpRequest);
        }
        if (httpRequest.isSameUri(PROFILE_REQUEST)) {
            return showProfile(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        if (httpRequest.isSameUri(USER_CREATE_REQUEST)) {
            return createUser(httpRequest);
        }
        if (httpRequest.isSameUri(LOGIN_REQUEST)) {
            return login(httpRequest);
        }

        return HttpResponse.of(Status.NOT_FOUND);
    }

    public static HttpResponse createUser(HttpRequest httpRequest) {
        Map<Header, String> headers = new HashMap<>();
        UserService service = UserService.getInstance();

        service.createUser(httpRequest);

        StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
        headers.put(Header.LOCATION, HOME_LOCATION);

        return HttpResponse.of(statusLine, headers, null);
    }

    private HttpResponse login(HttpRequest httpRequest) {
        UserService service = UserService.getInstance();

        try {
            User user = service.login(httpRequest);
            HttpSession httpSession = HttpSessions.getHttpSession(httpRequest.getSessionId());
            httpSession.setAttribute(USER_ATTRIBUTE_KEY, user);

            StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
            Map<Header, String> headers = new HashMap<>();
            headers.put(Header.LOCATION, HOME_LOCATION);
            headers.put(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());
            headers.put(Header.SET_COOKIE, LOGIN_SUCCESS_COOKIE_VALUE);

            return HttpResponse.of(statusLine, headers, null);
        } catch (LoginFailException e) {
            return redirectResponse(httpRequest, LOGIN_FAIL_REDIRECT_LOCATION);
        } catch (NoSessionException e) {
            return redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION);
        }
    }

    private HttpResponse showList(HttpRequest httpRequest) {
        if (isLogined(httpRequest)) {
            try {
                Template template = HandlebarUtils.loadHandlebar(LIST_REQUEST);
                Collection<User> users = DataBase.findAll();
                String listPage = template.apply(users);
                byte[] body = listPage.getBytes();

                StatusLine statusLine = StatusLine.of(httpRequest, Status.OK);
                Map<Header, String> headers = new HashMap<>();
                headers.put(Header.CONTENT_TYPE, ContentType.HTML
                    .getContentTypeValue());
                headers.put(Header.CONTENT_LENGTH, String.valueOf(body.length));

                return HttpResponse.of(statusLine, headers, body);
            } catch (IOException e) {
                return HttpResponse.of(Status.INTERNAL_ERROR);
            }
        }

        return redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION);
    }

    private HttpResponse showProfile(HttpRequest httpRequest) {
        if (isLogined(httpRequest)) {
            try {
                Template template = HandlebarUtils.loadHandlebar(PROFILE_REQUEST);
                HttpSession httpSession = HttpSessions.getHttpSession(httpRequest.getSessionId());
                User user = (User) httpSession.getAttribute(USER_ATTRIBUTE_KEY);
                String postPage = template.apply(user);
                byte[] body = postPage.getBytes();

                StatusLine statusLine = StatusLine.of(httpRequest, Status.OK);
                Map<Header, String> headers = new HashMap<>();
                headers.put(Header.CONTENT_LENGTH, String.valueOf(body.length));
                headers.put(Header.CONTENT_TYPE, ContentType.HTML
                    .getContentTypeValue());

                return HttpResponse.of(statusLine, headers, body);
            } catch (IOException e) {
                return HttpResponse.of(Status.INTERNAL_ERROR);
            } catch (NoSessionException e) {
                return redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION);
            }
        }

        return redirectResponse(httpRequest, LOGIN_REDIRECT_LOCATION);
    }

    private HttpResponse redirectResponse(HttpRequest httpRequest, String location) {
        StatusLine statusLine = StatusLine.of(httpRequest, Status.FOUND);
        Map<Header, String> headers = new HashMap<>();
        headers.put(Header.LOCATION, location);
        headers.put(Header.SET_COOKIE, LOGIN_FAIL_COOKIE_VALUE);

        return HttpResponse.of(statusLine, headers, null);
    }

    private boolean isLogined(HttpRequest httpRequest) {
        Optional<String> logined = httpRequest.getCookie(LOGIN_CHECK_COOKIE);
        if (logined.isPresent()) {
            return new Boolean(logined.get());
        }

        return false;
    }
}
