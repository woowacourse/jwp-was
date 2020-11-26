package controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import model.User;
import webserver.Cookie;
import webserver.HttpSession;
import webserver.controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        String userId = httpRequest.getBodyParameter("userId");
        String password = httpRequest.getBodyParameter("password");
        User user = DataBase.findUserById(userId);

        if (user.isSamePassword(password)) {
            HttpSession session = httpRequest.getSession();
            session.setAttribute("user", user);
            setSessionCookie(httpResponse, session);
            httpResponse.sendRedirect("/index.html");
            return;
        }
        httpResponse.sendRedirect("/user/login_failed.html");
    }

    private void setSessionCookie(HttpResponse httpResponse, HttpSession session) {
        Cookie cookie = new Cookie(HttpSession.DEFAULT_SESSION_NAME, session.getId());
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
    }
}
