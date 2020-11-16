package controller.user;

import java.io.IOException;

import controller.AbstractController;
import service.session.SessionService;
import service.user.UserService;
import session.HttpSession;
import user.User;
import webserver.HttpCookie;
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
        sessionService.add(httpSession);
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.add(String.format("SESSIONID=%s; Path=/", httpSession.getId()));
        httpCookie.apply(httpResponse);
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.sendRedirect("/index.html");
    }
}
