package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.session.HttpSession;
import webserver.session.HttpSessionHelper;
import webserver.view.RedirectView;
import webserver.view.View;

public class LoginServlet extends RequestServlet {
    private static final String REQUEST_PARAMS_USER_ID = "userId";
    private static final String REQUEST_PARAMS_PASSWORD = "password";
    private static final String COOKIE_USER_SESSION = "user_session";
    private static final String VIEW_LOGIN_SUCCESS = "/index.html";
    private static final String VIEW_LOGIN_FAIL = "/user/login_failed.html";
    private static final String SESSION_USER_NAME = "userId";

    @Override
    public View doPost(HttpRequest request, HttpResponse response) {
        String loginId = request.getBody(REQUEST_PARAMS_USER_ID);
        String loginPassword = request.getBody(REQUEST_PARAMS_PASSWORD);
        User user = DataBase.findUserById(loginId);
        if (user != null && user.isMatchPassword(loginPassword)) {
            return loginSuccess(request, response, user);
        }
        return loginFail(response);
    }

    private View loginSuccess(HttpRequest request, HttpResponse response, User user) {
        request.setAttribute("user", user);
        response.setCookie(COOKIE_USER_SESSION, getSessionId(user));
        return new RedirectView(VIEW_LOGIN_SUCCESS);
    }

    private String getSessionId(User user) {
        HttpSession userSession = new HttpSession();
        userSession.setAttribute(SESSION_USER_NAME, user.getUserId());
        return HttpSessionHelper.create(userSession);
    }

    private View loginFail(HttpResponse response) {
        response.removeCookie(COOKIE_USER_SESSION);
        return new RedirectView(VIEW_LOGIN_FAIL);
    }
}
