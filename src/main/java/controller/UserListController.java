package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;

import java.util.Collections;
import java.util.Map;

import static view.ViewResolver.REDIRECT_SIGNATURE;

public class UserListController extends AbstractController {
    public static UserListController getInstance() {
        return UserListControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLoginedUser(httpRequest.getSession())) {
            Map<String, Object> users = Collections.singletonMap("users", DataBase.findAll());
            return new ModelAndView("/user/list", users);
        }
        return new ModelAndView(String.format("%s%s", REDIRECT_SIGNATURE, "/"));

    }

    private boolean isLoginedUser(Session session) {
        return session.getAttribute("user") != null;
    }

    private static class UserListControllerLazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }
}
