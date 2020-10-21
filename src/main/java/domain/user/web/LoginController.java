package domain.user.web;

import java.io.IOException;

import controller.AbstractController;
import domain.user.model.User;
import domain.user.service.UserService;
import session.model.HttpSession;
import session.service.SessionService;
import webserver.HttpHeader;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class LoginController extends AbstractController {
    private final UserService userService;
    private final SessionService sessionService;

    public LoginController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.containsAll(User.USER_ID, User.PASSWORD)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.error();
            return;
        }
        HttpSession httpSession = new HttpSession();
        if (userService.findByUserId(httpRequest.getParameter(User.USER_ID)) == null) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpSession.setAttribute("logined", false);
            httpResponse.addHeader(HttpHeader.SET_COOKIE, String.format("SESSIONID=%s; Path=/", httpSession.getId()));
            httpResponse.sendRedirect("/user/login_failed.html");
        }
        if (userService.findByUserId(httpRequest.getParameter(User.USER_ID)) != null) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpSession.setAttribute("logined", true);
            httpResponse.addHeader(HttpHeader.SET_COOKIE, String.format("SESSIONID=%s; Path=/", httpSession.getId()));
            httpResponse.sendRedirect("/index.html");
        }
        sessionService.addSession(httpSession);
    }
}
