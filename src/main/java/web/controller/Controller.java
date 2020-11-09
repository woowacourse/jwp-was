package web.controller;

import web.request.HttpRequest;
import web.response.HttpResponse;
import web.view.ModelAndView;

public interface Controller {
    ModelAndView doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
