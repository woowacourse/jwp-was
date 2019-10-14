package http.controller;

import http.model.HttpRequest;
import http.model.HttpResponse;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest);
}
