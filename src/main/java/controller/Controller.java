package controller;

import http.request.HttpRequest;
import http.response.HttpResponseEntity;

public interface Controller {
    HttpResponseEntity handle(HttpRequest httpRequest);
}
