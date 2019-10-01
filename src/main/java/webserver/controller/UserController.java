package webserver.controller;

import webserver.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/user/form");
        return modelAndView;
    }
}
