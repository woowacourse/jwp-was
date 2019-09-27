package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;

import java.util.Collections;
import java.util.Map;

public class UserListController extends AbstractController {
    private static class UserListControllerLazyHolder {
        private static final UserListController INSTANCE = new UserListController();
    }

    public static UserListController getInstance() {
        return UserListControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView;
        if (isLoginedUser(httpRequest.getSession())) {
            Map<String, Object> users = Collections.singletonMap("users", DataBase.findAll());
            modelAndView = new ModelAndView("user/list", users);
        } else {
            modelAndView = new ModelAndView("redirect: /");
        }

        handle(modelAndView, httpResponse);
    }

    private boolean isLoginedUser(Session session) {
        return session.getAttribute("user") != null;
    }
}
