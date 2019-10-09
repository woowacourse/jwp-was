package web.controller.impl;

import org.slf4j.Logger;
import web.controller.AbstractController;
import web.db.DataBase;
import webserver.message.HttpCookie;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.session.HttpSession;
import webserver.session.SessionContextHolder;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;
import webserver.view.UrlBasedResourceView;

import static org.slf4j.LoggerFactory.getLogger;

public class LoginController extends AbstractController {
    private static final Logger LOG = getLogger(LoginController.class);
    private static final String TAG = "[ LoginController ]";

    private static final String SESSION_ID = "sessionId";

    @Override
    protected ModelAndView doGet(final Request request, final Response response) {
        return new ModelAndView(new UrlBasedResourceView("/user/login.html"));
    }

    @Override
    protected ModelAndView doPost(final Request request, final Response response) {
        final String userId = request.getQueryValue("userId");
        final String password = request.getQueryValue("password");

        LOG.info("{} userId: {}, password: {}", TAG, userId, password);

        if (!matchesUser(userId, password)) {
            response.addCookie(createCookie("logined", "false"));
            return new ModelAndView(new RedirectView("/user/login_failed.html"));
        }

        response.addCookie(createCookie("logined", "true"));
        response.addCookie(createCookie(SESSION_ID, enrollSession(userId).getId()));

        return new ModelAndView(new RedirectView("/"));
    }

    private boolean matchesUser(final String userId, final String password) {
        return DataBase.findUserById(userId)
                .filter(user -> user.matchesPassword(password))
                .isPresent();
    }

    private HttpCookie createCookie(final String key, final String value) {
        return new HttpCookie.Builder(key, value).path("/").build();
    }

    private HttpSession enrollSession(final String userId) {
        HttpSession session = HttpSession.newInstance();
        session.setAttribute("user", DataBase.findUserById(userId).get());
        SessionContextHolder.addSession(session);
        return session;
    }
}
