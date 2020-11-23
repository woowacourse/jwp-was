package controller;

import db.DataBase;
import exception.UserNotFoundException;
import model.User;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.session.HttpSession;
import web.session.SessionStore;
import web.session.WebSession;

public class LoginController extends AbstractController {
    public static final String INDEX_HTML_PATH = "/index.html";
    public static final String LOGIN_FAIL_HTML_PATH = "/user/login_failed.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            String userId = request.getRequestBodyByKey("userId");
            User user = DataBase.findUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            loginValidator(request, user);
            HttpSession session = new WebSession();
            session.setAttribute("email", user.getEmail());
            SessionStore.addSession(session);
            response.addSession(session);
            response.found(INDEX_HTML_PATH);
        } catch (IllegalAccessException | UserNotFoundException e) {
            logger.error(e.getMessage());
            response.found(LOGIN_FAIL_HTML_PATH);
        }
    }

    private void loginValidator(HttpRequest request, User user) throws IllegalAccessException {
        String password = request.getRequestBodyByKey("password");
        if (!user.checkPassword(password)) {
            throw new IllegalAccessException("로그인에 실패하였습니다.");
        }
    }
}
