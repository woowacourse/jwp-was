package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.TemplateView;

public class IndexController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static final String PATH = "/";

    @Override
    ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        return new ModelAndView(new TemplateView("index.html"));
    }
}
