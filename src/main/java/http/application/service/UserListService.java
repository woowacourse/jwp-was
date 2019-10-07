package http.application.service;

import db.DataBase;
import http.application.Service;
import http.common.HttpCookie;
import http.common.TemplateApplier;
import http.request.HttpCookies;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Collections;
import java.util.Map;

public class UserListService implements Service {
    private static final String REDIRECT_TEMPLATE = "/user/login.html";
    private static final String USER_LIST_TEMPLATE = "/user/list";
    private static final String LOGINED_COOKIE_KEY = "logined";

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogined(httpRequest.getCookies())) {
            Map<String, Object> model = Collections.singletonMap("users", DataBase.findAll());
            String appliedTemplate = TemplateApplier.apply(USER_LIST_TEMPLATE, model);
            httpResponse.forward(appliedTemplate.getBytes());
            return;
        }

        httpResponse.redirect(REDIRECT_TEMPLATE);
    }

    private boolean isLogined(HttpCookies cookies) {
        HttpCookie httpCookie = cookies.get(LOGINED_COOKIE_KEY);
        if (httpCookie != null) {
            return Boolean.parseBoolean(httpCookie.getValue());
        }

        return false;
    }
}
