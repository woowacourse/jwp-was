package webserver.controller;

import db.DataBase;
import utils.TemplateFactory;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaderType;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Collections;

public class UserListController extends AbstractController {
    private static final String COOKIE_LOGINED = "logined=true";
    private static final String USER_LIST_PATH = "user/list";
    private static final String REDIRECT_PATH = "/";

    public UserListController() {
        this.paths = Collections.singletonList("/user/list");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String cookieHeader = httpRequest.getHeader(HttpHeader.of(HttpHeaderType.COOKIE));
        if (cookieHeader.contains(COOKIE_LOGINED)) {
            String template = TemplateFactory.apply(USER_LIST_PATH, DataBase.findAll());
            byte[] body = template.getBytes();
            httpResponse.forward(body);
        } else {
            httpResponse.sendRedirect(REDIRECT_PATH);
        }
    }
}
