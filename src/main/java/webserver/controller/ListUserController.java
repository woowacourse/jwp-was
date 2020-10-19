package webserver.controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import model.User;
import utils.HandlebarUtils;
import webserver.Cookie;
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
        Optional<Cookie> loginedCookie = cookies.getCookie(LOGIN_COOKIE_NAME);

        if (loginedCookie.isPresent() && Boolean.parseBoolean(loginedCookie.get().getValue())) {
            final Collection<User> users = DataBase.findAll();
            String appliedPage = HandlebarUtils.apply(USER_LIST_URL, users);
            httpResponse.forwardByHandlebars(USER_LIST_URL + ".html", appliedPage);
            return;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
