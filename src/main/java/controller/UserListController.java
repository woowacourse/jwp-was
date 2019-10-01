package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        if (httpRequest.containHeaderField("Cookie", "logined=true")) {
            log.debug("login success");
            Map<String, Object> model = new HashMap<>();
            Collection<User> users = DataBase.findAll();
            model.put("users", users);

            modelAndView.setView("/user/list");
            modelAndView.setModel(model);
            return modelAndView;
        }
        modelAndView.setView("redirect:/user/login.html");
        log.debug("login fail");
        return modelAndView;
    }
}
