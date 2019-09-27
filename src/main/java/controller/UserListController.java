package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import session.Session;
import session.Sessions;
import webserver.resolver.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class UserListController extends BasicController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Session session = Sessions.getSession(request.getCookieValue("JSESSIONID"));

        if(isLogined(session)) {
            List<User> users = new ArrayList<>(DataBase.findAll());
            modelAndView.put("users", users);
            modelAndView.addView("/user/list");
        } else {
            modelAndView.addView("/user/login");
        }

        byte[] body = HandlebarsGenerator.render(modelAndView);
        response.okResponse("html", body);
    }

    private Boolean isLogined(Session session) {
        Object attribute = session.getAttribute("logined");
        return attribute != null && (Boolean) attribute;
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }

}
