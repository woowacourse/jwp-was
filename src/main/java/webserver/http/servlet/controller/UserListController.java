package webserver.http.servlet.controller;

import static java.util.Objects.*;

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

    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        String cookie = request.getCookie();
        if (!isNull(cookie) && cookie.contains(LOGINED_TRUE)) {
            HttpRequestLine requestLine = request.getRequestLine();
            RequestURI uri = requestLine.getRequestURI();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("users", DataBase.findAll());
            return HandlebarView.of(uri, new Model(attributes));
        }
        return new RedirectView("/user/login.html");
    }
}
