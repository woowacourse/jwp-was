package web.controller.impl;

import org.slf4j.Logger;
import web.controller.AbstractController;
import web.db.DataBase;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.session.HttpSession;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;
import webserver.view.UrlBasedResourceView;

import static org.slf4j.LoggerFactory.getLogger;

public class LoginController extends AbstractController {
    private static final Logger LOG = getLogger(LoginController.class);
    private static final String TAG = "[ LoginController ]";

    private static final String LOGINED = "logined";

    @Override
    protected ModelAndView doGet(final Request request, final Response response) {
        return new ModelAndView(new UrlBasedResourceView("/user/login.html"));
    }

    @Override
    protected ModelAndView doPost(final Request request, final Response response) {
        final HttpSession session = request.getSession();
        final String userId = request.getQueryValue("userId");
        final String password = request.getQueryValue("password");

        LOG.info("{} userId: {}, password: {}", TAG, userId, password);

        if (!matchesUser(userId, password)) {
            session.setAttribute(LOGINED, false);
            return new ModelAndView(new RedirectView("/user/login_failed.html"));
        }
        session.setAttribute(LOGINED, true);
        session.setAttribute("user", DataBase.findUserById(userId).get());
        return new ModelAndView(new RedirectView("/"));
    }

    private boolean matchesUser(final String userId, final String password) {
        return DataBase.findUserById(userId)
                .filter(user -> user.matchesPassword(password))
                .isPresent();
    }
}
