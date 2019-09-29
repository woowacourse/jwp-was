package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;
import model.User;
import view.ModelAndView;

public class LoginController extends AbstractController {
    private static class LoginControllerLazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }

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

        if (user != null && user.matchPassword(password)) {
            Session session = httpRequest.getSession();
            session.setAttribute("user", user);
            httpResponse.addHeaderAttribute("Set-Cookie", "SessionID=" + session.getId() + "; Path=/");
            return new ModelAndView("redirect: /");
        }

        return new ModelAndView("redirect: /user/login_failed");
    }
}
