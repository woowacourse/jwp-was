package server.controller;

import db.DataBase;
import server.web.HttpSession;
import server.web.controller.Controller;
import server.web.controller.RequestMapping;
import server.web.model.Model;
import server.web.request.HttpMethod;
import server.web.request.HttpRequest;
import server.web.response.HttpResponse;
import server.web.view.HandlebarView;
import server.web.view.ModelAndView;

import java.util.Optional;

@RequestMapping(uri = "/user/list", httpMethod = HttpMethod.GET)
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
