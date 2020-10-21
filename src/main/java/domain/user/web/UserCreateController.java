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

public class UserCreateController extends AbstractController {
    private final UserService userService;
    private final SessionService sessionService;

    public UserCreateController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.containsAll(User.USER_ID, User.PASSWORD, User.NAME, User.EMAIL)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.error();
            return;
        }
        User user = new User(httpRequest.getParameter(User.USER_ID), httpRequest.getParameter(User.PASSWORD),
            httpRequest.getParameter(User.NAME), httpRequest.getParameter(User.EMAIL));
        userService.addUser(user);
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("logined", true);
        sessionService.addSession(httpSession);

        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeader.SET_COOKIE, String.format("SESSIONID=%s; Path=/", httpSession.getId()));
        httpResponse.sendRedirect("/index.html");
    }
}
