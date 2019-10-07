package webserver.controller;

import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest) throws IOException;
}
