package controller;

import web.request.HttpRequest;
import web.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
