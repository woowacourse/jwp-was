package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import session.Session;
import template.HandlebarsGenerator;
import template.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static controller.LoginController.LOGINED;

public class UserListController extends BasicController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Session session = request.getSession();

        if (isLogined(session)) {
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
        Object attribute = session.getAttribute(LOGINED);
        return attribute != null && (Boolean) attribute;
    }
}
