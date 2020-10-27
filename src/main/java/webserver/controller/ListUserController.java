package webserver.controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import model.User;
import utils.HandlebarUtils;
import webserver.Cookies;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ListUserController extends AbstractController {

    private static final String LOGIN_COOKIE_NAME = "logined";
    private static final String USER_LIST_URL = "/user/list";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        Cookies cookies = new Cookies(httpRequest.getCookies());

        boolean logined = cookies.getCookie(LOGIN_COOKIE_NAME)
            .map(cookie -> Boolean.parseBoolean(cookie.getValue()))
            .orElse(false);

        if (logined) {
            final Collection<User> users = DataBase.findAll();
            String appliedPage = HandlebarUtils.apply(USER_LIST_URL, users);
            httpResponse.forwardByHandlebars(USER_LIST_URL + ".html", appliedPage);
            return;
        }

        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
