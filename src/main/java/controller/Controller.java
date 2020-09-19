package controller;

import http.request.HttpRequest;
import view.ModelAndView;

public interface Controller {

    ModelAndView handle(HttpRequest httpRequest);

    boolean canHandle(HttpRequest httpRequest);
}
