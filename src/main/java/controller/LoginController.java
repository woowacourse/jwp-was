package controller;

import controller.exception.NotFoundUserIdException;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import model.exception.InvalidPasswordException;
import view.RedirectView;
import view.View;
import webserver.SessionManager;

import static com.google.common.net.HttpHeaders.SET_COOKIE;

public class LoginController extends AbstractController {
    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            User user = DataBase.findUserById(httpRequest.getRequestBody("userId"))
                    .orElseThrow(NotFoundUserIdException::new);

            user.matchPassword(httpRequest.getRequestBody("password"));
            HttpSession session = SessionManager.createEmptySession();
            session.setAttributes("user", user);
            SessionManager.addSession(session);
            httpResponse.addHeader(SET_COOKIE, String.format("SESSIONID=%s; Path=/", session.getId()));

            return new RedirectView("index.html");
        } catch (InvalidPasswordException | NotFoundUserIdException e) {
            httpResponse.addHeader(SET_COOKIE, "SESSIONID=''; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");
            return new RedirectView("user/login_failed.html");
        }
    }
}