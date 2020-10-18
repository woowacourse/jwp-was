package controller;

import db.DataBase;
import exception.IllegalRequestException;
import http.HttpHeaders;
import http.request.Request;
import http.request.RequestBody;
import http.response.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(Request request, Response response) {
        try {
            RequestBody requestBody = request.getRequestBody();
            Map<String, String> requestBodies = requestBody.parseRequestBody();
            User user = DataBase.findUserById(requestBodies.get("userId"));
            validate(requestBodies, user);

            response.setHeader(HttpHeaders.SET_COOKIE, "logined=true; Path=/");
            response.found("/index.html");
        } catch (IllegalRequestException e) {
            logger.info(e.getMessage());
            response.setHeader(HttpHeaders.SET_COOKIE, "logined=false");
            response.found("/user/login_failed.html");
        }
    }

    private void validate(Map<String, String> requestBodies, User user) throws IllegalRequestException {
        if (user == null || !user.checkPassword(requestBodies.get("password"))) {
            throw new IllegalRequestException("로그인에 실패했습니다.");
        }
    }
}
