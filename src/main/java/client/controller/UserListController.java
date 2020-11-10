package client.controller;

import db.DataBase;
import web.HttpSession;
import web.controller.Controller;
import web.model.Model;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.view.HandlebarView;
import web.view.ModelAndView;

import java.util.Optional;

public class UserListController implements Controller {

    @Override
    public ModelAndView doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getSession();
        boolean logined = (boolean) Optional.ofNullable(httpSession.getAttribute("logined"))
                .orElse(false);

        if (logined) {
            Model model = new Model();
            model.setAttribute("users", DataBase.findAll());

            return new ModelAndView(model, new HandlebarView("user/profile"));
        }
        return ModelAndView.view(new HandlebarView("redirect:/user/login.html"));
    }
}
