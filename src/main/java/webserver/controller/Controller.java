package webserver.controller;

import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
