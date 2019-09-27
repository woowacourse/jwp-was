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
import session.HttpSessionDB;
import utils.QueryStringUtils;

import java.util.Map;

public class UserLoginController extends AbstractController {
    public static final String SESSION_ID = "SessionId";

    private UserService userService = new UserService();

    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> body = QueryStringUtils.parse(request.getBody());

        try {
            User foundUser = userService.login(body.get("userId"), body.get("password"));

            HttpSession httpSession = HttpSessionDB.getInstance().findOrCreateSession(request.getCookieValue(SESSION_ID));
            setCookie(response, httpSession);

            httpSession.setAttribute("login-user", foundUser);
            ResponseResolver.resolve(new RedirectView("/index.html"), response);
        } catch (AuthorizationFailException e) {
            ResponseResolver.resolve(new RedirectView("/user/login_failed.html"), response);
        }
    }

    private void setCookie(HttpResponse response, HttpSession httpSession) {
        HttpCookie httpCookie = new HttpCookie(SESSION_ID, httpSession.getId());
        httpCookie.setPath("/");
        response.addCookie(httpCookie);
    }
}
