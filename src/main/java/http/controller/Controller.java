package http.controller;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    HttpResponse get(HttpRequest httpRequest);

    HttpResponse post(HttpRequest httpRequest);
}
