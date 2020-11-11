package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.domain.User;
import model.general.ContentType;
import model.general.Header;
import model.general.Method;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import service.UserService;
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
        //todo: enum으로 처리?
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
        Map<Header, String> headers = new HashMap<>();
        UserService service = UserService.getInstance();
        StatusLine statusLine;

        try {
            User user = service.login(httpRequest);
            HttpSession httpSession = HttpSessions.getHttpSession(httpRequest.getSessionId());
            httpSession.setAttribute("user", user);

            statusLine = StatusLine.of(httpRequest, Status.FOUND);
            headers.put(Header.LOCATION, HOME_LOCATION);
            headers.put(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());
            headers.put(Header.SET_COOKIE, "logined=true; Path=/");
        } catch (IllegalArgumentException e) {
            statusLine = StatusLine.of(httpRequest, Status.FOUND);
            headers.put(Header.LOCATION, LOGIN_FAIL_REDIRECT_LOCATION);
            headers.put(Header.SET_COOKIE, "logined=false; Path=/");
        }

        return HttpResponse.of(statusLine, headers, null);
    }

    private HttpResponse showList(HttpRequest httpRequest) {
        Map<Header, String> headers = new HashMap<>();
        StatusLine statusLine;
        String logined = httpRequest.getCookie("logined");
        byte[] body;

        if ("true".equals(logined)) {
            statusLine = StatusLine.of(httpRequest, Status.OK);
            headers.put(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = null;
            try {
                template = handlebars.compile("user/list");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Collection<User> users = DataBase.findAll();
            String listPage = null;
            try {
                listPage = template.apply(users);
            } catch (IOException e) {
                e.printStackTrace();
            }

            body = listPage.getBytes();
            headers.put(Header.CONTENT_LENGTH, String.valueOf(body.length));
            return HttpResponse.of(statusLine, headers, body);
        }

        statusLine = StatusLine.of(httpRequest, Status.FOUND);
        headers.put(Header.LOCATION, LOGIN_REDIRECT_LOCATION);
        return HttpResponse.of(statusLine, headers, null);
    }

    private HttpResponse showProfile(HttpRequest httpRequest) {
        Map<Header, String> headers = new HashMap<>();
        StatusLine statusLine;
        String logined = httpRequest.getCookie("logined");
        byte[] body;

        if ("true".equals(logined)) {
            statusLine = StatusLine.of(httpRequest, Status.OK);
            headers.put(Header.CONTENT_TYPE, ContentType.HTML
                .getContentTypeValue());

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = null;
            try {
                template = handlebars.compile("user/profile");
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpSession httpSession = HttpSessions.getHttpSession(httpRequest.getSessionId());
            User user = (User) httpSession.getAttribute("user");

            String postPage = null;
            try {
                postPage = template.apply(user);
            } catch (IOException e) {
                e.printStackTrace();
            }

            body = postPage.getBytes();
            headers.put(Header.CONTENT_LENGTH, String.valueOf(body.length));
            return HttpResponse.of(statusLine, headers, body);
        }

        statusLine = StatusLine.of(httpRequest, Status.FOUND);
        headers.put(Header.LOCATION, LOGIN_REDIRECT_LOCATION);
        return HttpResponse.of(statusLine, headers, null);
    }
}
