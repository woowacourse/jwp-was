package webserver.controller;

import db.DataBase;
import utils.TemplateFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSessionStorage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserListController extends AbstractController {
    private static final String COOKIE_JSESSIONID = "jsessionid";
    private static final String USER_LIST_PATH = "user/list";
    private static final String REDIRECT_PATH = "/user/login.html";

    public UserListController() {
        this.paths = Collections.singletonList("/user/list");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            String[] cookieAttributes = parseCookie(httpRequest);
            List<String> jSessionCookies = Arrays.stream(cookieAttributes)
                    .filter(attribute -> attribute.contains(COOKIE_JSESSIONID))
                    .collect(Collectors.toList());
            if (HttpSessionStorage.isValidSession(jSessionCookies)) {
                String template = TemplateFactory.apply(USER_LIST_PATH, DataBase.findAll());
                byte[] body = template.getBytes();
                httpResponse.forward(body);
                return;
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            httpResponse.sendRedirect(REDIRECT_PATH);
        }
    }
}
