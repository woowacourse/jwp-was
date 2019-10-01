package controller;

import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class MainController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/index");
        return modelAndView;
    }
}
