package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.exception.NotFoundHttpRequestHeader;
import http.response.HttpResponse;
import model.User;
import webserver.resolver.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class UserListController extends BasicController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String cookie = request.getHeader("Cookie");
            if (cookie.contains("logined=true")) {
                List<User> users = new ArrayList<>(DataBase.findAll());
                modelAndView.put("users", users);
                modelAndView.addView("/user/list");
            }
            if (cookie.contains("logined=false")) {
                modelAndView.addView("/user/login");
            }
        } catch (NotFoundHttpRequestHeader e) {
            modelAndView.addView("/user/login");
        }
        byte[] body = HandlebarsGenerator.render(modelAndView);
        response.okResponse("html", request.getVersion(), body);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        throw new BadRequestException();
    }
}
