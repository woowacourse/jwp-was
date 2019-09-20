package controller;

import http.request.HttpRequest;
import http.response.HttpResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    HttpResponseEntity handle(HttpRequest httpRequest);
}
