package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.StaticResourceHandler;

import static http.request.HttpMethod.GET;
import static http.request.HttpMethod.POST;
import static http.response.HttpStatus.METHOD_NOT_ALLOWED;

public abstract class AbstractController implements Controller {
    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (GET.equals(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
        }
        if (POST.equals(httpRequest.getMethod())) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        forwardErrorPage(response);
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        forwardErrorPage(response);
    }

    private void forwardErrorPage(HttpResponse response) {
        response.setStatus(METHOD_NOT_ALLOWED);
        StaticResourceHandler.forwardErrorPage(response);
    }
}
