package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    ModelAndView service(HttpRequest httpRequest, HttpResponse httpResponse);
}
