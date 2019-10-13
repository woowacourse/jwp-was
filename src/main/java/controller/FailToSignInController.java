package controller;

import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.HtmlView;

public class FailToSignInController extends AbstractController {

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new HtmlView("/user/login_failed"));
        return modelAndView;
    }
}
