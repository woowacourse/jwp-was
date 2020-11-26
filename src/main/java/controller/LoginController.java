package controller;

import annotation.RequestMapping;
import dto.LoginRequestDto;
import exception.UnAuthenticationException;
import http.HttpBody;
import http.HttpSession;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

@RequestMapping(path = "/user/login")
public class LoginController extends AbstractController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpBody httpBody = httpRequest.getBody();
        String userId = httpBody.get("userId");
        String password = httpBody.get("password");
        LoginRequestDto loginRequestDto = new LoginRequestDto(userId, password);
        try {
            userService.login(loginRequestDto);
            loginSuccess(httpRequest, httpResponse);
        } catch (UnAuthenticationException e) {
            loginFail(httpResponse);
        }
    }

    private void loginSuccess(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.MOVED_PERMANENTLY);
        HttpSession session = httpRequest.getSession();
        session.setAttribute("logined", "true");
        httpResponse.addHeader("Location", "/");
    }

    private void loginFail(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.MOVED_PERMANENTLY);
        httpResponse.addHeader("Location", "/user/login_failed.html");
    }
}
