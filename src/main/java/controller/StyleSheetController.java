package controller;

import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.StyleSheetView;

public class StyleSheetController extends AbstractController {
    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new StyleSheetView());
        return modelAndView;
    }
}
