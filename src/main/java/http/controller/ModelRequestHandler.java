package http.controller;

import http.model.HttpRequest;
import http.supoort.RequestMapping;
import http.view.ModelAndView;

public class ModelRequestHandler extends AbstractController {

    public ModelRequestHandler(RequestMapping requestMapping) {
        super(requestMapping);
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        return null;
    }
}
