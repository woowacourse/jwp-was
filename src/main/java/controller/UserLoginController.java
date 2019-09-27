package controller;

import db.DataBase;
import http.Cookie;
import http.request.Request;
import http.response.Response;
import http.support.HttpStatus;

public class UserLoginController extends AbstractController {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED = "logined";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String PATH = "/";
    private static final String USER_LOGIN_FAILED_URL = "/user/login_failed.html";
    private static final String INDEX_URL = "/index.html";

    @Override
    public void doPost(Request request, Response response) {
        String userId = request.getParameter(USER_ID);
        String password = request.getParameter(PASSWORD);
        Cookie cookie = new Cookie(LOGINED, TRUE);
        cookie.setPath(PATH);

        if (!DataBase.isContainId(userId) || !DataBase.isRightPassword(userId, password)) {
            cookie.setValue(FALSE);
            response.addCookie(cookie);
            response.setHttpStatus(HttpStatus.FOUND);
            response.sendRedirect(USER_LOGIN_FAILED_URL, HttpStatus.FOUND);
            return;
        }

        response.addCookie(cookie);
        response.setHttpStatus(HttpStatus.FOUND);
        response.sendRedirect(INDEX_URL, HttpStatus.FOUND);
    }
}
