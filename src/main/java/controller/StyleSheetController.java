package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.StyleSheetView;

public class StyleSheetController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(StyleSheetController.class);

    @Override
    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new StyleSheetView());
        return modelAndView;
    }
}
