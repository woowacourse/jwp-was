package user.controller;

import static java.util.Objects.*;

import java.util.HashMap;
import java.util.Map;

import user.db.DataBase;
import webserver.controller.Controller;
import webserver.http.HttpHeaderFields;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestLine;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;
import webserver.model.Model;
import webserver.servlet.AbstractView;
import webserver.servlet.HandlebarView;
import webserver.servlet.RedirectView;

public class UserListController implements Controller {
    private static final String LOGINED_TRUE = "logined=true";
    private static final String USERS = "users";
    private static final String TO_LOGIN = "/user/login.html";

    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        String cookieValues = request.getHeader().get(HttpHeaderFields.COOKIE);

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
