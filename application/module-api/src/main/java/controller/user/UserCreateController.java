package controller.user;

import java.io.IOException;

import annotations.Controller;
import annotations.RequestMapping;
import controller.AbstractController;
import cookie.HttpCookie;
import domain.HttpMethod;
import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import service.user.UserService;
import session.HttpSession;
import user.User;

@Controller
public class UserCreateController extends AbstractController {
    private final UserService userService;

    public UserCreateController() {
        userService = UserService.getInstance();
    }

    public UserCreateController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
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
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.add(String.format("SESSIONID=%s; Path=/", httpSession.getId()));
        httpCookie.apply(httpResponse);
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.sendRedirect("/index.html");
    }
}
