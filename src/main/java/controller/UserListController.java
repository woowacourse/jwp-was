package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.Model;
import view.ModelAndView;
import view.RedirectView;
import view.View;

import java.util.Collection;

public class UserListController extends AbstractController {

    @Override
    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String cookie = httpRequest.getHeaderAttribute("Cookie");
        if (cookie != null && cookie.contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            Model model = new Model();
            model.setAttribute("users", users);

            return new ModelAndView(model, "user/list");
        }

        return new RedirectView("user/login.html");
    }
}
