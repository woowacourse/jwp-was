package controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Objects;
import model.User;
import utils.HandlebarUtils;
import webserver.HttpSession;
import webserver.controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ListUserController extends AbstractController {

    private static final String USER_LIST_URL = "/user/list";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {

        if (isLogin(httpRequest.getSession())) {
            Collection<User> users = DataBase.findAll();
            String appliedPage = HandlebarUtils.apply(USER_LIST_URL, users);
            httpResponse.forwardByHandlebars(USER_LIST_URL + ".html", appliedPage);
            return;
        }

        httpResponse.sendRedirect("/user/login_failed.html");
    }

    private boolean isLogin(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");

        return Objects.nonNull(user);
    }
}
