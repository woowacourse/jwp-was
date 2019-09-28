package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;
import model.User;

public class LoginController extends AbstractController {
    private static class LoginControllerLazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }

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
            httpResponse.addHeaderAttribute("Set-Cookie", "SessionID=" + session.getId() + "; Path=/");
            modelAndView = new ModelAndView("redirect: /");
        } else {
            modelAndView = new ModelAndView("redirect: /user/login_failed");
        }

        handle(modelAndView, httpResponse);
    }
}
