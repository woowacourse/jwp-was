package http.controller;

import db.DataBase;
import http.common.Cookie;
import http.common.HttpSession;
import http.common.SessionManager;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String LOGINED = "logined";
    private static final String TRUE = "true";
    private static final String JSESSIONID = "JSESSIONID";
    private static final int ONE_DAY = 60 * 60 * 24;

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        if (DataBase.findUserById(userId) != null && password.equals(DataBase.findUserById(userId).getPassword())) {
            setSession(httpResponse);
            httpResponse.redirect("/index.html");
            logger.debug("Successful Login: {}", userId);
            return;
        }
        httpResponse.redirect("/user/login_failed.html");
        logger.debug("Failed Login: {}", userId);
    }

    private void setSession(HttpResponse httpResponse) {
        HttpSession httpSession = SessionManager.createSession();
        httpSession.setAttribute(LOGINED, TRUE);
        Cookie cookie = new Cookie(JSESSIONID, httpSession.getId());
        cookie.setMaxAge(ONE_DAY);
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
    }
}
