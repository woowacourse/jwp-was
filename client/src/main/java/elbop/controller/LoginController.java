package elbop.controller;

import elbop.db.DataBase;
import elbop.model.User;
import server.web.controller.Controller;
import server.web.controller.RequestMapping;
import server.web.request.HttpMethod;
import server.web.request.HttpRequest;
import server.web.response.HttpResponse;
import server.web.view.HandlebarView;
import server.web.view.ModelAndView;

import java.util.Optional;

@RequestMapping(uri = "/user/login", httpMethod = HttpMethod.POST)
public class LoginController implements Controller {

    @Override
    public ModelAndView doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getRequestBody().getParameter("userId");
        String password = httpRequest.getRequestBody().getParameter("password");

        User findUser = Optional.ofNullable(DataBase.findUserById(userId))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s: 존재하지 않는 사용자입니다.", userId)));
        boolean logined = findUser.hasSamePassword(password);

        httpRequest.getSession()
                .setAttribute("logined", logined);

        if (logined) {
            return ModelAndView.view(new HandlebarView("redirect:/index.html"));
        }
        return ModelAndView.view(new HandlebarView("redirect:/user/login_failed.html"));
    }
}
