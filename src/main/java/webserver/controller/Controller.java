package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {
    void doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
