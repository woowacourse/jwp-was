package web.controller.impl;

import org.slf4j.Logger;
import web.controller.AbstractController;
import web.db.DataBase;
import webserver.StaticFile;
import webserver.message.exception.NotFoundFileException;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.message.response.ResponseCookie;
import webserver.session.HttpSession;
import webserver.session.SessionContextHolder;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class LoginController extends AbstractController {
    private static final Logger LOG = getLogger(LoginController.class);
    private static final String TAG = "[ LoginController ]";

    private static final String INDEX_PAGE_URL = "/";
    private static final String TEMPLATES_PATH = "./templates";
    private static final String USER_LOGIN_PAGE = "/user/login.html";
    private static final String LOGIN_FAILED_PAGE = "/user/login_failed.html";
    private static final String SESSION_ID = "sessionId";

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
        final String userId = request.getQueryValue("userId");
        final String password = request.getQueryValue("password");

        LOG.info("{} userId: {}, password: {}", TAG, userId, password);

        if (!matchesUser(userId, password)) {
            return new Response.Builder().redirectUrl(LOGIN_FAILED_PAGE).addCookie(createCookie("logined", "false")).build();
        }

        return new Response.Builder()
                .redirectUrl(INDEX_PAGE_URL)
                .addCookie(createCookie("logined", "true"))
                .addCookie(createCookie(SESSION_ID, enrollSession(userId).getId()))
                .build();
    }

    private boolean matchesUser(final String userId, final String password) {
        return DataBase.findUserById(userId)
                .filter(user -> user.matchesPassword(password))
                .isPresent();
    }

    private ResponseCookie createCookie(final String key, final String value) {
        return new ResponseCookie.Builder(key, value).path("/").build();
    }

    private HttpSession enrollSession(final String userId) {
        HttpSession session = HttpSession.newInstance();
        session.setAttribute("user", DataBase.findUserById(userId).get());
        SessionContextHolder.addSession(session);
        return session;
    }
}
