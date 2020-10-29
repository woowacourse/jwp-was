package controller;

import exception.IllegalPasswordException;
import exception.LoginException;
import exception.NotFoundUserIdException;
import http.AbstractServlet;
import http.HttpSession;
import http.SessionContainer;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import model.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String PATH = "/user/login";
    public static final String SESSION_KEY_OF_LOGIN = "logined";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession session = SessionContainer.getInstance()
                .getSession(httpRequest.getCookie(SessionContainer.SESSION_KEY_FOR_COOKIE));
        try {
            String userId = httpRequest.getParameter("userId");
            String password = httpRequest.getParameter("password");

            validateLoginRequests(userId, password);

            logger.debug("Login success");

            session.setAttribute(SESSION_KEY_OF_LOGIN, Boolean.toString(true));
            httpResponse.sendRedirect("/index.html");
        } catch (LoginException e) {
            logger.error(e.getMessage());
            session.setAttribute(SESSION_KEY_OF_LOGIN, Boolean.toString(false));
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
