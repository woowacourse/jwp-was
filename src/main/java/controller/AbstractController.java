package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.StaticResourceHandler;

public abstract class AbstractController implements Controller {
    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.match(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
        }
        if (HttpMethod.POST.match(httpRequest.getMethod())) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        StaticResourceHandler.handle404NotFound(httpResponse);
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        StaticResourceHandler.handle404NotFound(httpResponse);
    }
}
