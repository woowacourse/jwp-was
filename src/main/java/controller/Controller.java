package controller;

import http.request.HttpRequest;
import http.response.response_entity.HttpResponseEntity;

public interface Controller {
    HttpResponseEntity handle(HttpRequest httpRequest);
}
