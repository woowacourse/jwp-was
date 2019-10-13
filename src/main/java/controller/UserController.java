package controller;

import utils.HttpSessionUtils;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.HtmlView;
import webserver.view.RedirectView;

public class UserController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        if (HttpSessionUtils.isLogined(httpRequest)) {
            modelAndView.setView(new RedirectView("/index.html"));
            return modelAndView;
        }
        modelAndView.setView(new HtmlView("/user/form"));
        return modelAndView;
    }
}
