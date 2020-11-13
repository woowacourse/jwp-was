package controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import exceptions.UserNotFound;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;
import model.User;

public class LoginController extends HttpRequestMappingAbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String LOGINED_TRUE = "logined=true; Path=/";
    private static final String LOGINED_FALSE = "logined=false";
    private static final String LOGIN_SUCCESS_HTML = "/index.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";

    public LoginController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String userId = httpRequest.getParameter("userId");
            String password = httpRequest.getParameter("password");

            validateUser(userId, password);

            httpResponse.setCookie(LOGINED_TRUE);
            httpResponse.redirect(LOGIN_SUCCESS_HTML);

        } catch (Exception e) {
            logger.error(e.getMessage());
            httpResponse.setCookie(LOGINED_FALSE);
            httpResponse.redirect(LOGIN_FAILED_HTML);
        }
    }

    private void validateUser(String userId, String password) {
        User user = DataBase.findUserById(userId).orElseThrow(UserNotFound::new);

        if (!authenticateUser(user, password)) {
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }

    }

    private boolean authenticateUser(User user, String password) {
        return Objects.nonNull(user) && user.validatePassword(password);
    }
}
