package controller;

import annotation.RequestMapping;
import dto.LoginRequestDto;
import exception.UnAuthenticationException;
import http.HttpBody;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
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
            loginSuccess(httpResponse);
        } catch (UnAuthenticationException e) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED);
            loginFail(httpResponse);
        }
    }

    private void loginSuccess(HttpResponse httpResponse) {
        httpResponse.setCookie("logined=true; Path=/");
    }

    private void loginFail(HttpResponse httpResponse) {
        httpResponse.setCookie("logined=false");
    }
}
