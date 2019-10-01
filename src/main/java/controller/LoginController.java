package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;
import model.User;

import static view.RedirectViewMatcher.REDIRECT_SIGNATURE;

public class LoginController extends AbstractController {
    public static final String LOGIN_SUCCESS_REDIRECT_LOCATION = "/";
    public static final String LOGIN_FAILED_REDIRECT_LOCATION = "/user/login_failed";

    public static LoginController getInstance() {
        return LoginControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("/user/login");
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getFormDataParameter("userId");
        String password = httpRequest.getFormDataParameter("password");

        User user = DataBase.findUserById(userId);

        if (user == null || !user.matchPassword(password)) {
            return new ModelAndView(String.format("%s%s", REDIRECT_SIGNATURE, LOGIN_FAILED_REDIRECT_LOCATION));
        }

        Session session = httpRequest.getSession();
        session.setAttribute("user", user);
        return new ModelAndView(String.format("%s%s", REDIRECT_SIGNATURE, LOGIN_SUCCESS_REDIRECT_LOCATION));
    }

    private static class LoginControllerLazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }
}
