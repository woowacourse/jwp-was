package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import webserver.AuthenticationException;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.getBodyValue("userId");
        String password = httpRequest.getBodyValue("password");

        DataBase.findUserById(userId)
                .filter(user -> user.isSamePassword(password))
                .orElseThrow(() -> new AuthenticationException("사용자의 아이디 또는 비밀번호가 올바르지 않습니다."));

        httpResponse.sendRedirect("/index.html");
        httpResponse.noContent();
    }
}
