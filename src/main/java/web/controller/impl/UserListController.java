package web.controller.impl;

import web.controller.AbstractController;
import web.db.DataBase;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.session.SessionContextHolder;
import webserver.view.HandleBarView;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;

public class UserListController extends AbstractController {
    private static final String LOGINED = "logined";

    @Override
    protected ModelAndView doGet(final Request request, final Response response) {
        if (!isLogined(request)) {
            return new ModelAndView(new RedirectView("/user/login"));
        }

        ModelAndView modelAndView = new ModelAndView(new HandleBarView("/user/list"));
        modelAndView.addModel("users", DataBase.findAll());

        return modelAndView;
    }

    private boolean isLogined(Request request) {
        return request.getCookieValue(LOGINED).equals("true")
                && SessionContextHolder.findSessionById(request.getCookieValue("sessionId")).isPresent();
    }
}
