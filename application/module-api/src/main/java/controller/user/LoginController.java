package controller.user;

import java.io.IOException;
import java.util.Objects;

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
public class LoginController extends AbstractController {
    private final UserService userService;

    public LoginController() {
        userService = UserService.getInstance();
    }

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.containsAll(User.USER_ID, User.PASSWORD)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.error();
            return;
        }
        setHttpResponse(httpRequest, httpResponse);
    }

    private void setHttpResponse(HttpRequest httpRequest, HttpResponse httpResponse) throws
        IOException {
        User user = userService.findByUserId(httpRequest.getParameter(User.USER_ID));
        HttpCookie httpCookie = new HttpCookie();
        HttpSession httpSession = httpRequest.getHttpSession();
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpCookie.add(String.format("SESSIONID=%s; Path=/", httpSession.getId()));
        httpCookie.apply(httpResponse);
        if (Objects.isNull(user)) {
            httpSession.setAttribute("logined", false);
            httpResponse.sendRedirect("/user/login_failed.html");
        }
        if (Objects.nonNull(user)) {
            httpSession.setAttribute("logined", true);
            httpResponse.sendRedirect("/index.html");
        }
    }
}
