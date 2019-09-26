package http.controller;

import db.DataBase;
import http.common.HttpCookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.HandleBarModelAndView;
import utils.ModelAndView;

import java.io.IOException;

public class UserListController extends AbstractController {
    private static final String LOCATION = "/user/list";
    private static final String LOGIN_NAME = "logined";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpCookie requestHttpCookie = httpRequest.getHttpCookie();
        if ("true".equals(requestHttpCookie.getCookie(LOGIN_NAME))) {
            try {
                ModelAndView modelAndView = new HandleBarModelAndView();
                modelAndView.putData("users", DataBase.findAll());

                httpResponse.setResponseBody(modelAndView.render(LOCATION), LOCATION + modelAndView.getSufFix());

                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        httpResponse.redirect("/index.html");
    }
}
