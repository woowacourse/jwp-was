package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.session.Session;

import java.io.IOException;
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

        try {
            setHttpResponse(modelAndView, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isLoginedUser(Session session) {
        return session.getAttribute("user") != null;
    }
}
