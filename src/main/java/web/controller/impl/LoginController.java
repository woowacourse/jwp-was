package web.controller.impl;

import org.slf4j.Logger;
import web.controller.AbstractController;
import web.db.DataBase;
import web.model.User;
import webserver.StaticFile;
import webserver.message.exception.NotFoundFileException;
import webserver.message.request.Request;
import webserver.message.request.RequestBody;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class LoginController extends AbstractController {
    private static final Logger LOG = getLogger(LoginController.class);

    private static final String NOT_FOUND_USER_ID_MESSAGE = "해당 사용자가 존재하지 않습니다.";
    private static final String UNMATCHED_USER_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String INDEX_PAGE_URL = "/";
    private static final String LOGIN_FAILED_PAGE_URL = "/user/login_failed.html";
    private static final String TEMPLATES_PATH = "./templates";
    private static final String USER_LOGIN_PAGE = "/user/login.html";

    @Override
    protected Response doGet(Request request) {
        try {
            return new Response.Builder().body(new StaticFile(TEMPLATES_PATH + USER_LOGIN_PAGE)).build();
        } catch (IOException | URISyntaxException e) {
            throw new NotFoundFileException();
        }
    }

    @Override
    protected Response doPost(final Request request) {
        final RequestBody body = request.getBody();
        final String userId = body.getQueryValue("userId");
        final String password = body.getQueryValue("password");
        final Optional<User> user = DataBase.findUserById(userId);

        if (!matchesUser(user, password)) {
            return new Response.Builder().redirectUrl(LOGIN_FAILED_PAGE_URL).setCookie("logined=false; Path=/").build();
        }

        /* TODO ExceptionResolver를 통해 예외를 받아서 처리하도록 수정하기
        final User user = DataBase.findUserById(userId).orElseThrow(() -> new LoginException(NOT_FOUND_USER_ID_MESSAGE));

        validLoginUser(user, password);
        */

        return new Response.Builder()
                .redirectUrl(INDEX_PAGE_URL)
                .setCookie("logined=true; Path=/")
                .build();
    }

    private boolean matchesUser(Optional<User> user, String password) {
        return user.isPresent() && user.get().matchesPassword(password);
    }

    /*private void validLoginUser(Optional<User> user, String password) throws LoginException {
        if (user.isEmpty()) {
            throw new LoginException(NOT_FOUND_USER_ID_MESSAGE);
        }
        if (!user.get().matchesPassword(password)) {
            throw new LoginException(UNMATCHED_USER_MESSAGE);
        }
    }*/
}
