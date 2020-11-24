package webserver.http.servlet.controller;

import static java.util.Objects.*;
import static webserver.http.HttpHeaderFields.*;

import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestLine;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractView;
import webserver.http.servlet.HandlebarView;
import webserver.http.servlet.Model;
import webserver.http.servlet.RedirectView;

public class UserListController implements Controller {
    private static final String LOGINED_TRUE = "logined=true";
    private static final String USERS = "users";
    private static final String TO_LOGIN = "/user/login.html";

    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        String cookieValues = request.getHeader().get(COOKIE);

        if (nonNull(cookieValues) && cookieValues.contains(LOGINED_TRUE)) {
            HttpRequestLine requestLine = request.getRequestLine();
            RequestURI uri = requestLine.getRequestURI();
            return HandlebarView.of(uri, initModel());
        }
        return new RedirectView(TO_LOGIN);
    }

    private Model initModel() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(USERS, DataBase.findAll());
        return new Model(attributes);
    }
}
