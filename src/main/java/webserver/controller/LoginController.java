package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.util.Map;
import java.util.Optional;

public class LoginController extends AbstractController{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGIN_SUCCESS_INDEX = "/index.html";
    private static final String LOGIN_FAILED_INDEX = "/user/login_failed.html";

    private LoginController () {
    }

    public static LoginController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {

        private static final LoginController INSTANCE = new LoginController();
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> requestBodyFields = httpRequest.getBodyFields();
        String userId = requestBodyFields.get(USER_ID);
        String password = requestBodyFields.get(PASSWORD);
        logger.debug(">>> {} , {}", userId, password);
        sendLoginPage(httpResponse, userId, password);
    }

    private void sendLoginPage(HttpResponse httpResponse, String userId, String password) {
        Optional<User> maybeUser = DataBase.findUserById(userId);

        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            boolean isMatchPassword = user.isMatchPassword(password);
            httpResponse.sendRedirect(LOGIN_SUCCESS_INDEX, isMatchPassword);
            return;
        }

        String redirectUrl = LOGIN_FAILED_INDEX;
        httpResponse.sendRedirect(redirectUrl, false);
    }
}
