package controller;

import controller.exception.NotFoundUserException;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String PATH = "/user/login";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getQueryValue("userId");
        String userPassword = httpRequest.getQueryValue("password");

        try {
            Optional.ofNullable(DataBase.findUserById(userId))
                    .filter(user -> user.matchPassword(userPassword))
                    .orElseThrow(() -> new NotFoundUserException(userId));
            HttpSession httpSession = httpRequest.getHttpSession();
            httpSession.setAttribute("logined", "true");
            httpResponse.setCookie("JSESSIONID=" + httpSession.getId() + "; Path=/");
            httpResponse.redirect("/index.html");
        } catch (NotFoundUserException e) {
            logger.debug(e.getMessage());
            httpRequest.getHttpSession().invalidate();
            httpResponse.redirect("/user/login_failed.html");
        }
    }
}
