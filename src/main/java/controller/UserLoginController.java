package controller;

import http.HttpCookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.view.RedirectView;
import model.AuthorizationFailException;
import model.User;
import service.UserService;
import session.HttpSession;
import session.HttpSessionManager;
import utils.QueryStringUtils;

import java.util.Map;

public class UserLoginController extends AbstractController {
    public static final String LOGIN_USER = "login-user";

    private UserService userService = new UserService();

    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> body = QueryStringUtils.parse(request.getBody());

        try {
            User foundUser = userService.login(body.get("userId"), body.get("password"));

            HttpSession httpSession = request.getSession(HttpSessionManager.getInstance());
            setSessionToCookie(response, httpSession);

            httpSession.setAttribute(LOGIN_USER, foundUser);
            ResponseResolver.resolve(new RedirectView("/index.html"), response);
        } catch (AuthorizationFailException e) {
            ResponseResolver.resolve(new RedirectView("/user/login_failed.html"), response);
        }
    }

    private void setSessionToCookie(HttpResponse response, HttpSession httpSession) {
        HttpCookie httpCookie = new HttpCookie(httpSession);
        httpCookie.setPath("/");
        response.addCookie(httpCookie);
    }
}
