package controller;

import controller.exception.LoginUserException;
import db.DataBase;
import http.HttpSession;
import http.HttpSessionStore;
import http.RedirectView;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginUserController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserController.class);

    public static final String PATH = "/user/login";

    @Override
    RedirectView doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        HttpSession session = HttpSessionStore.getSession(httpRequest.getSessionId());

        try {
            User user = Optional.ofNullable(DataBase.findUserById(userId))
                    .orElseThrow(() ->new LoginUserException("Not Found User"));
            if (!user.checkPassword(password)) {
                throw new LoginUserException("password not match");
            }

            session.addAttribute("logined", "true");
        } catch (LoginUserException e) {
            LOGGER.debug("login error");
            session.addAttribute("logined", "false");
            e.printStackTrace();
        }

        return new RedirectView("/");
    }
}
