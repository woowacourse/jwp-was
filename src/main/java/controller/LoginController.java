package controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import exception.IllegalRequestException;
import http.request.Cookie;
import http.request.Request;
import http.request.RequestBody;
import http.response.Response;
import http.session.HttpSession;
import http.session.WebSession;
import model.User;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(Request request, Response response) {
        try {
            RequestBody requestBody = request.getRequestBody();
            Map<String, String> requestBodies = requestBody.parseRequestBody();
            User user = DataBase.findUserById(requestBodies.get("userId"));
            validate(requestBodies, user);

            HttpSession httpSession = request.getHttpSession(true);
            httpSession.setAttribute("email", user.getEmail());

            String id = httpSession.getId();
            Cookie cookie = new Cookie(WebSession.DEFAULT_SESSION_COOKIE_NAME, id + "; path=/");

            response.addCookie(cookie);
            response.found("/index.html");
        } catch (IllegalRequestException e) {
            logger.info(e.getMessage());
            response.found("/user/login_failed.html");
        }
    }

    private void validate(Map<String, String> requestBodies, User user) throws IllegalRequestException {
        if (user == null || !user.checkPassword(requestBodies.get("password"))) {
            throw new IllegalRequestException("로그인에 실패했습니다.");
        }
    }
}
