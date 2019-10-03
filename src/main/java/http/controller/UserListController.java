package http.controller;

import db.DataBase;
import exception.MethodNotAllowedException;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.HandlebarsView;
import view.ModelAndView;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String LOGINED = "logined";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogined(httpRequest)) {
            ModelAndView mav = new ModelAndView(new HandlebarsView("user/list.html"));
            mav.addObject("users", DataBase.findAll());
            mav.render(httpRequest, httpResponse);
            return;
        }
        httpResponse.redirect("/user/login.html");
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    private boolean isLogined(HttpRequest httpRequest) {
        HttpSession httpSession = httpRequest.getSession();
        Object logined = httpSession.getAttribute(LOGINED);
        return logined != null && (boolean) logined;
    }
}
