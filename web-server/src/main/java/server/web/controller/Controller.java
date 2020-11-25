package server.web.controller;

import server.web.request.HttpRequest;
import server.web.response.HttpResponse;
import server.web.view.ModelAndView;

public interface Controller {
    ModelAndView doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
