package controller;

import controller.exception.HttpMethodNotAllowedException;
import controller.exception.URINotFoundException;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import view.View;

public abstract class AbstractController implements Controller {
    @Override
    public View service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.matchMethod(HttpMethod.POST)) {
            return doPost(httpRequest, httpResponse);
        }
        if (httpRequest.matchMethod(HttpMethod.GET)) {
            return doGet(httpRequest, httpResponse);
        }
        throw new HttpMethodNotAllowedException();
    }

    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new URINotFoundException();
    }

    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new URINotFoundException();
    }
}