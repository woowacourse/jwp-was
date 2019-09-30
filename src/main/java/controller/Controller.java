package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.view.View;

public interface Controller {
    View service(HttpRequest request, HttpResponse response);
}
