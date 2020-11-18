package controller;

import db.UserRepository;
import exception.IllegalRequestException;
import http.HttpHeaders;
import http.request.Request;
import http.response.Response;
import http.session.HttpSession;
import http.session.HttpSessionStore;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doPost(Request request, Response response) {
        try {
            User user = UserRepository.findByUserId(request.getParam("userId"))
                .orElseThrow(() -> new IllegalRequestException("로그인에 실패했습니다."));
            String password = request.getParam("password");
            validate(user, password);

            HttpSession httpSession = HttpSessionStore.create();
            httpSession.setAttribute("email", user.getEmail());

            String id = httpSession.getId();

            response.setHeader(HttpHeaders.SET_COOKIE, "JSESSIONID=" + id + "; Path=/");
            response.found("/index.html");
        } catch (IllegalRequestException e) {
            LOGGER.info(e.getMessage());
            response.found("/user/login_failed.html");
        }
    }

    private void validate(User user, String password) throws IllegalRequestException {
        if (user == null || !user.checkPassword(password)) {
            throw new IllegalRequestException("로그인에 실패했습니다.");
        }
    }
}
