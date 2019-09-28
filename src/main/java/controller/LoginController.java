package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;
import model.User;

import static http.common.HttpHeader.SESSIONID;
import static view.ViewResolver.REDIRECT_SIGNATURE;

public class LoginController extends AbstractController {
    public static LoginController getInstance() {
        return LoginControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        handle(new ModelAndView("/user/login"), httpResponse);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getFormDataParameter("userId");
        String password = httpRequest.getFormDataParameter("password");

        User user = DataBase.findUserById(userId);
        ModelAndView modelAndView;

        if (user != null && user.matchPassword(password)) {
            Session session = httpRequest.getSession();
            session.setAttribute("user", user);
            httpResponse.addCookie(SESSIONID, session.getId());
            httpResponse.addCookie("Path", "/");
            modelAndView = new ModelAndView(String.format("%s%s", REDIRECT_SIGNATURE, "/"));
        } else {
            modelAndView = new ModelAndView(String.format("%s%s", REDIRECT_SIGNATURE, "/user/login_failed"));
        }

        handle(modelAndView, httpResponse);
    }

    private static class LoginControllerLazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }
}
