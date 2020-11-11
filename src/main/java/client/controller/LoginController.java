package client.controller;

import db.DataBase;
import model.User;
import web.controller.Controller;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.view.HandlebarView;
import web.view.ModelAndView;

import java.util.Optional;

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
