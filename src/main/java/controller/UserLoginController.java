package controller;

import http.HTTP;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.view.RedirectView;
import model.AuthorizationFailException;
import model.User;
import service.UserService;
import session.HttpSession;
import session.HttpSessionFactory;
import utils.QueryStringUtils;

public class UserLoginController extends AbstractController {

    private UserService userService = new UserService();

    @Override
    void doGet(HttpRequest request, HttpResponse response) {

    }

    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        String userId = QueryStringUtils.parse(request.getBody()).get("userId");
        String password = QueryStringUtils.parse(request.getBody()).get("password");
        try {
            User foundUser = userService.login(userId, password);

            HttpSession httpSession = HttpSessionFactory.create();
            httpSession.setAttribute("login-user", foundUser);
            response.render(new RedirectView("/index.html"));
            response.addHeader(HTTP.SET_COOKIE, httpSession.getId());

        } catch (AuthorizationFailException e) {
            response.render(new RedirectView("/user/login_failed.html"));
        }
    }
}
