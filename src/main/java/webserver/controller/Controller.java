package webserver.controller;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest);
}
