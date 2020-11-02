package domain.user.web;

import java.io.IOException;

import controller.AbstractController;
import domain.user.model.User;
import domain.user.service.UserService;
import session.model.HttpSession;
import session.service.SessionService;
import webserver.HttpCookie;
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
        setHttpResponse(httpRequest, httpResponse, httpSession);
        sessionService.addSession(httpSession);
    }

    private void setHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse, HttpSession httpSession) throws
        IOException {
        User user = userService.findByUserId(httpRequest.getParameter(User.USER_ID));
        HttpCookie httpCookie = new HttpCookie();
        if (user == null) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpSession.setAttribute("logined", false);
            httpCookie.add(String.format("SESSIONID=%s; Path=/", httpSession.getId()));
            httpCookie.apply(httpResponse);
            httpResponse.sendRedirect("/user/login_failed.html");
        }
        if (user != null) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpSession.setAttribute("logined", true);
            httpCookie.add(String.format("SESSIONID=%s; Path=/", httpSession.getId()));
            httpCookie.apply(httpResponse);
            httpResponse.sendRedirect("/index.html");
        }
    }
}
