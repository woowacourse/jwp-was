package domain.controller;

import domain.db.UserDataBase;
import domain.model.User;
import domain.service.LoginService;
import mvc.annotation.RequestMapping;
import mvc.controller.AbstractController;
import mvc.view.ModelAndView;
import mvc.view.RedirectView;
import mvc.view.View;
import server.http.request.HttpRequest;
import was.http.context.Session;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(urlPath = "/user/list")
public class UserListController extends AbstractController {
    @Override
    public View get(HttpRequest httpRequest) {
        Session session = httpRequest.getSession();
        if (session.get("login") != null && session.get("login").equals("true")) {
            List<User> users = new ArrayList<>(UserDataBase.findAll());
            return new ModelAndView("user/profile", users);
        }
        return new RedirectView("/user/login");
    }
}
