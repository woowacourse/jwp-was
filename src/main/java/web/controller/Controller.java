package web.controller;

import web.request.HttpRequest;
import web.response.HttpResponse;

public interface Controller {
    void doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
