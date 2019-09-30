package controller;

import controller.exception.NotFoundUserIdException;
import db.DataBase;
import http.Cookie;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import model.exception.InvalidPasswordException;
import view.RedirectView;
import view.View;
import webserver.SessionManager;

public class LoginController extends AbstractController {
    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            User user = DataBase.findUserById(httpRequest.getRequestBody("userId"))
                    .orElseThrow(NotFoundUserIdException::new);
            user.matchPassword(httpRequest.getRequestBody("password"));

            HttpSession session = SessionManager.findSession(httpRequest.getSessionId());
            session.setAttributes("user", user);
            Cookie cookie = new Cookie("logined", "true");
            httpResponse.addCookie(cookie);

            return new RedirectView("index.html");
        } catch (InvalidPasswordException | NotFoundUserIdException e) {
            Cookie cookie = new Cookie("logined", "false");
            httpResponse.addCookie(cookie);
            return new RedirectView("user/login_failed.html");
        }
    }
}