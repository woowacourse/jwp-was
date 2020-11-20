package controller;

import db.DataBase;
import exception.UserNotFoundException;
import model.User;
import web.HttpHeader;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class LoginController extends AbstractController {
    public static final String INDEX_HTML_PATH = "/index.html";
    public static final String LOGIN_FAIL_HTML_PATH = "/user/login_failed.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            loginValidator(request);
            response.addHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");
            response.found(INDEX_HTML_PATH);
        } catch (IllegalAccessException | UserNotFoundException e) {
            logger.error(e.getMessage());
            response.addHeader(HttpHeader.SET_COOKIE, "logined=false");
            response.found(LOGIN_FAIL_HTML_PATH);
        }
    }

    private void loginValidator(HttpRequest request) throws IllegalAccessException {
        String userId = request.getRequestBodyByKey("userId");
        String password = request.getRequestBodyByKey("password");
        User foundUser = DataBase.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (!foundUser.checkPassword(password)) {
            throw new IllegalAccessException("로그인에 실패하였습니다.");
        }
    }
}
