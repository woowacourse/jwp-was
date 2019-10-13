package controller;

import utils.HttpSessionUtils;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        if (HttpSessionUtils.isLogined(httpRequest)) {
            modelAndView.setView("redirect:/index.html");
            return modelAndView;
        }
        modelAndView.setView("/user/form");
        return modelAndView;
    }
}
