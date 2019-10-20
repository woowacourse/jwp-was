package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.HttpSession;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.HtmlView;
import webserver.view.RedirectView;

import static controller.support.Constants.*;

public class SignInController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(SignInController.class);

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession httpSession = httpRequest.getHttpSession();
        if (LOGINED_VALUE_TRUE.equals(httpSession.getAttribute(LOGINED_KEY))) {
            modelAndView.setView(new RedirectView("/index.html"));
            return modelAndView;
        }
        modelAndView.setView(new HtmlView("/user/login"));
        return modelAndView;
    }

    @Override
    public String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(USER_PASSWORD);

        if (!DataBase.contains(userId)) {
            httpResponse.addCookie(LOGINED_KEY, LOGINED_VALUE_FALSE);
            log.debug("Not Found UserId");
            return REDIRECT_PREFIX + "/user/login_failed.html";
        }

        User user = DataBase.findUserById(userId);
        if (user.matchPassword(password)) {
            HttpSession httpSession = httpRequest.getHttpSession();
            httpSession.setAttribute(LOGINED_KEY, LOGINED_VALUE_TRUE);
            httpSession.setAttribute("Path", "/");

            log.debug("{} login", userId);
            return REDIRECT_PREFIX + "/index.html";
        }

        httpResponse.addCookie(LOGINED_KEY, LOGINED_VALUE_FALSE);
        log.debug("Not Match UserId And Password");
        return REDIRECT_PREFIX + "/user/login_failed.html";
    }
}
