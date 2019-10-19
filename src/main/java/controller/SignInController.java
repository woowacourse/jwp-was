package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpSessionUtils;
import webserver.common.HttpSession;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.HtmlView;
import webserver.view.RedirectView;

import static webserver.request.RequestHeader.SESSION_KEY;

public class SignInController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(SignInController.class);
    private static final String LOGINED_KEY = "logined";
    private static final String LOGINED_VALUE_TRUE = "true";
    private static final String LOGINED_VALUE_FALSE = "false";

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        if (HttpSessionUtils.isLogined(httpRequest)) {
            modelAndView.setView(new RedirectView("/index.html"));
            return modelAndView;
        }
        modelAndView.setView(new HtmlView("/user/login"));
        return modelAndView;
    }

    @Override
    public String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        if (!DataBase.contains(userId)) {
            httpResponse.addCookie(LOGINED_KEY, LOGINED_VALUE_FALSE);
            log.debug("Not Found UserId");
            return "redirect:/user/login_failed.html";
        }

        User user = DataBase.findUserById(userId);
        if (user.matchPassword(password)) {
            HttpSession httpSession = httpRequest.getHttpSession();
            httpSession.setAttribute(LOGINED_KEY, LOGINED_VALUE_TRUE);
            httpSession.setAttribute("Path", "/");
            httpResponse.addCookie(SESSION_KEY, httpSession.getId());

            log.debug("{} login", userId);
            return "redirect:/index.html";
        }

        httpResponse.addCookie(LOGINED_KEY, LOGINED_VALUE_FALSE);
        log.debug("Not Match UserId And Password");
        return "redirect:/user/login_failed.html";
    }
}
