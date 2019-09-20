package http.controller;

import http.model.HttpRequest;
import http.view.ModelAndView;

public interface Controller {
    ModelAndView handle(HttpRequest httpRequest);

    boolean canHandle(HttpRequest httpRequest);
}
