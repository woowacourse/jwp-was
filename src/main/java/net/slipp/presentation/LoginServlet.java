package net.slipp.presentation;

import net.slipp.application.UserService;
import net.slipp.application.UserServiceFactory;
import net.slipp.application.exception.AuthenticationFailException;
import net.slipp.presentation.dto.LoginRequest;
import net.slipp.presentation.dto.parser.RequestDTOParser;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.model.servlet.HttpServlet;

public class LoginServlet extends HttpServlet {

    private static final String INDEX_URL_PATH = "/index.html";
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final String LOGIN_COOKIE_PATH = "/";

    private static final String COOKIE_NAME_LOGINED = "logined";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        LoginRequest loginRequest = RequestDTOParser.toLoginRequest(request);
        UserService userService = UserServiceFactory.getInstance();
        try {
            login(response, loginRequest, userService);
        } catch (AuthenticationFailException e) {
            loginFail(response);
        }
    }

    private void login(HttpResponse response, LoginRequest loginRequest, UserService userService) {
        userService.login(loginRequest);
        response.addCookie(new Cookie(COOKIE_NAME_LOGINED, true, LOGIN_COOKIE_PATH));
        response.sendRedirect(INDEX_URL_PATH);
    }

    private void loginFail(HttpResponse response) {
        response.addCookie(new Cookie(COOKIE_NAME_LOGINED, false));
        response.sendRedirect(LOGIN_FAIL_PATH);
    }
}
