package http.controller;

import db.DataBase;
import http.common.HttpCookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import modelandview.HandleBarModelAndView;
import modelandview.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private static final String LOCATION = "/user/list";
    private static final String LOGIN_NAME = "logined";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpCookie requestHttpCookie = httpRequest.getHttpCookie();
        if ("true".equals(requestHttpCookie.getCookie(LOGIN_NAME))) {
            try {
                ModelAndView modelAndView = new HandleBarModelAndView();
                modelAndView.putData("users", DataBase.findAll());

                httpResponse.sendOk(modelAndView.render(LOCATION), LOCATION + modelAndView.getSufFix());

                return;
            } catch (IOException e) {
                logger.error("/user/list IOException : {}", e.getMessage());
            }
        }

        httpResponse.redirect("/index.html");
    }
}
