package controller;

import http.TemplateView;
import http.View;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static final String PATH = "/";

    @Override
    View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        return new TemplateView("index.html");
    }
}
