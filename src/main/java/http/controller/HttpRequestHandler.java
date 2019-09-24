package http.controller;

import http.model.HttpRequest;
import http.view.ModelAndView;

public interface HttpRequestHandler {
    ModelAndView handle(HttpRequest httpRequest);
}
