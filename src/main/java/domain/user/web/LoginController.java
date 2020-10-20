package domain.user.web;

import java.io.IOException;

import controller.AbstractController;
import domain.user.model.User;
import domain.user.service.UserService;
import webserver.HttpHeader;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class LoginController extends AbstractController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.containsAll(User.USER_ID, User.PASSWORD)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.error();
        }
        if (userService.findByUserId(httpRequest.getParameter(User.USER_ID)) == null) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpResponse.addHeader(HttpHeader.SET_COOKIE, "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
        if (userService.findByUserId(httpRequest.getParameter(User.USER_ID)) != null) {
            httpResponse.setHttpStatus(HttpStatus.FOUND);
            httpResponse.addHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");
            httpResponse.sendRedirect("/index.html");
        }
    }
}
