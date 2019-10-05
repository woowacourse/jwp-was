package controller;

import controller.exception.LoginUserException;
import db.DataBase;
import http.HttpCookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class LoginUserController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserController.class);

    public static final String PATH = "/user/login";

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        try {
            User user = Optional.ofNullable(DataBase.findUserById(userId))
                    .orElseThrow(() ->new LoginUserException("Not Found User"));
            if (!user.checkPassword(password)) {
                throw new LoginUserException("password not match");
            }

            httpResponse.setCookie(new HttpCookie("logined", "true"));
            httpResponse.redirect("/");
        } catch (LoginUserException e) {

            httpResponse.setCookie(new HttpCookie("logined", "false"));
            httpResponse.redirect("/");
            e.printStackTrace();
        }
    }
}
