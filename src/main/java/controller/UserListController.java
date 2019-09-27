package controller;

import db.DataBase;
import http.request.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.resolver.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class UserListController extends BasicController {
    // TODO: 2019-09-26  
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = request.createCookie();
        if (cookie.isLogined()) {
            List<User> users = new ArrayList<>(DataBase.findAll());
            modelAndView.put("users", users);
            modelAndView.addView("/user/list");
        } else {
            modelAndView.addView("/user/login");
        }
        byte[] body = HandlebarsGenerator.render(modelAndView);
        response.okResponse("html", body);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }
}
