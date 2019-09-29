package webserver.controller;

import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest);
}
