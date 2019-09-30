package http.controller;

import db.DataBase;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String LOGINED = "logined";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        if (DataBase.findUserById(userId) != null && password.equals(DataBase.findUserById(userId).getPassword())) {
            setSession(httpRequest, httpResponse);
            httpResponse.redirect("/index.html");
            logger.debug("Successful Login: {}", userId);
            return;
        }
        httpResponse.redirect("/user/login_failed.html");
        logger.debug("Failed Login: {}", userId);
    }

    private void setSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getSession();
        httpSession.setAttribute(LOGINED, true);
        httpResponse.setSession(httpSession);
    }
}
