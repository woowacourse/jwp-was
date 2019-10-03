package http.application.service;

import db.DataBase;
import http.application.Service;
import http.common.HttpCookie;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.request.MessageBody;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private static final String COOKIE_PATH = "/";

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        MessageBody messageBody = httpRequest.getMessageBody();
        String userId = messageBody.get("userId");
        String password = messageBody.get("password");

        User user = DataBase.findUserById(userId);
        boolean isAuthorized = authorize(user, password);

        String redirectUrl = isAuthorized ? "/index.html" : "/user/login_failed.html";

        httpResponse.redirect(redirectUrl);
        httpResponse.setCookie(HttpCookie.builder("logined", isAuthorized + "").path(COOKIE_PATH).build());

        if (isAuthorized) {
            HttpSession httpSession = httpRequest.getHttpSession();
            httpSession.setAttribute("user", user);
        }
        logger.info("login {}! redirect to {}", isAuthorized, redirectUrl);
    }

    private boolean authorize(User user, String password) {
        return user != null && user.authorize(password);
    }
}
