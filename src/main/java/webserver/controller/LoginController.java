package webserver.controller;

import http.request.Request;
import http.response.Cookie;
import http.response.Response;
import http.session.Session;
import model.LoginService;
import model.exception.LoginFailException;

public class LoginController extends HttpController {
    private LoginController() {
    }

    public static LoginController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doPost(Request request, Response response) {
        Session session = request.getSession();
        try {
            String location = new LoginService().login(request.extractFormData());
            session.setAttribute("logined", "true");
            response.setCookie("JSESSIONID",
                    Cookie.builder().name("JSESSIONID").value(session.getSessionId()).path("/").build());
            response.redirect(location);
        } catch (LoginFailException e) {
            session.setAttribute("logined", "false");
            response.setCookie("JSESSIONID",
                    Cookie.builder().name("JSESSIONID").value(session.getSessionId()).path("/").build());
            response.redirect("/user/login_failed.html");
        }
    }

    private static class LazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }
}
