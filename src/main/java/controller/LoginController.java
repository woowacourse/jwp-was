package controller;

import exception.IllegalPasswordException;
import exception.LoginException;
import exception.NotFoundUserIdException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import model.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String PATH = "/user/login";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String userId = httpRequest.getParameter("userId");
            String password = httpRequest.getParameter("password");

            validateLoginRequests(userId, password);

            logger.debug("Login success");
            httpResponse.putHeader("Set-Cookie", "logined=true; path=/");
            httpResponse.sendRedirect("/index.html");
        } catch (LoginException e) {
            logger.error(e.getMessage());
            httpResponse.putHeader("Set-Cookie", "logined=false");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }

    private void validateLoginRequests(String userId, String password) {
        User user = DataBase.findUserById(userId).orElseThrow(NotFoundUserIdException::new);
        if (user.isWrongPassword(password)) {
            throw new IllegalPasswordException();
        }
    }

    @Override
    protected String getPath() {
        return PATH;
    }
}
