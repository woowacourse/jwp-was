package webserver.controller;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
