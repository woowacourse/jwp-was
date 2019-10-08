package controller;

import com.google.common.collect.Maps;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import session.Session;
import view.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static http.Cookie.LOGINED;


public class UserListController extends BasicController {
    @Override
    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
        Session session = request.getSession();

        if (isLogined(session)) {
            List<User> users = new ArrayList<>(DataBase.findAll());
            Map<String, Object> model = Maps.newHashMap();
            model.put("users", users);
            return new ModelAndView("/user/list", model);
        }
        return new ModelAndView("redirect: /user/login.html");
    }

    private Boolean isLogined(Session session) {
        Object attribute = session.getAttribute(LOGINED);
        return Objects.nonNull(attribute) && (Boolean) attribute;
    }
}
