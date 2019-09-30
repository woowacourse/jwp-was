package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;
import webserver.session.HttpSession;
import webserver.session.HttpSessionHelper;

public class LoginServlet extends RequestServlet {
    private static final String REQUEST_PARAMS_USER_ID = "userId";
    private static final String REQUEST_PARAMS_PASSWORD = "password";
    private static final String COOKIE_USER_SESSION = "user_session";
    private static final String VIEW_LOGIN_SUCCESS = "/index.html";
    private static final String VIEW_LOGIN_FAIL = "/user/login_failed.html";
    private static final String SESSION_USER_NAME = "userId";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        String loginId = httpRequest.getBody(REQUEST_PARAMS_USER_ID);
        String loginPassword = httpRequest.getBody(REQUEST_PARAMS_PASSWORD);
        User user = DataBase.findUserById(loginId);
        if (user != null && user.isMatchPassword(loginPassword)) {
            return loginSuccess(user);
        }
        return loginFail();
    }

    private HttpResponse loginSuccess(User user) {
        ResponseHeader header = new ResponseHeader();
        header.setCookie(COOKIE_USER_SESSION, getSessionId(user));
        header.setLocation(VIEW_LOGIN_SUCCESS);
        return HttpResponse.found(header);
    }

    private String getSessionId(User user) {
        HttpSession userSession = new HttpSession();
        userSession.setAttribute(SESSION_USER_NAME, user.getUserId());
        return HttpSessionHelper.create(userSession);
    }

    private HttpResponse loginFail() {
        ResponseHeader header = new ResponseHeader();
        header.removeCookie(COOKIE_USER_SESSION);
        header.setLocation(VIEW_LOGIN_FAIL);
        return HttpResponse.found(header);
    }
}
