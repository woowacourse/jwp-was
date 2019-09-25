package controller;

import controller.exception.NotFoundUserException;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
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
            httpResponse.redirect("/index.html");
            httpResponse.setCookie("logined=true;");
        } catch (NotFoundUserException e) {
            logger.debug(e.getMessage());
            httpResponse.setCookie("logined=false;");
            httpResponse.redirect("/user/login_failed.html");
        }
    }
}
