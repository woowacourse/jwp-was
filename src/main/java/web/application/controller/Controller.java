package web.application.controller;

import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
