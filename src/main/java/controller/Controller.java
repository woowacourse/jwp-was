package controller;

import http.HttpRequest;
import http.HttpResponse;
import view.View;

public interface Controller {
    View service(HttpRequest httpRequest, HttpResponse httpResponse);
}