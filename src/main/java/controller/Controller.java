package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    ModelAndView service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
