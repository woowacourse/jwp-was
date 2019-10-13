package controller;

import db.DataBase;
import http.HttpSession;
import http.HttpSessionStore;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.HandlebarView;
import view.ModelAndView;
import view.RedirectView;
import view.View;

import java.util.Collection;

public class UserListController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserListController.class);

    public static final String PATH = "/user/list";

    @Override
    ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        LOGGER.debug("userList request get: {}", httpRequest.getUri());

        ModelAndView modelAndView = new ModelAndView();
        View view = new RedirectView("login_failed.html");

        HttpSession session = HttpSessionStore.getSession(httpRequest.getSessionId());
        String sessionAttribute = (String) session.getAttributes("logined");

        LOGGER.debug("session id: {}", session.getId());
        LOGGER.debug("session attribute, {}", sessionAttribute);

        if (sessionAttribute.equals("true")) {
            Collection<User> users = DataBase.findAll();
            modelAndView.addAttribute("userList", users);

            view = new HandlebarView("list.html");
        }

        modelAndView.setView(view);
        return modelAndView;
    }
}
