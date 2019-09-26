package webserver.controller;

import http.request.Request;
import http.response.Cookie;
import http.response.Response;
import http.session.Session;
import http.session.Sessions;
import model.LoginService;
import model.exception.LoginFailException;
import webserver.exception.InvalidRequestMethodException;
import webserver.support.CookieParser;

import java.util.Map;

public class LoginController extends HttpController {
    private LoginController() {
    }

    public static LoginController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doGet(Request request, Response response) {
        throw new InvalidRequestMethodException();
    }

    @Override
    protected void doPost(Request request, Response response) {
        Map<String, String> cookies = CookieParser.parse(request.extractHeader("Cookie"));
        Session session = Sessions.getInstance().getSession(cookies.get("JSESSIONID"));
        try {
            String location = new LoginService().login(request.extractFormData());
            response.configureFoundResponse(location);
            session.setSessionAttribute("logined", "true");
            response.setCookie("JSESSIONID", new Cookie("JSESSIONID", session.getSessionId(), "/"));
        } catch (LoginFailException e) {
            response.configureFoundResponse("/user/login_failed.html");
            session.setSessionAttribute("logined", "false");
            response.setCookie("JSESSIONID", new Cookie("JSESSIONID", session.getSessionId(), "/"));
        }
    }

    private static class LazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }
}
