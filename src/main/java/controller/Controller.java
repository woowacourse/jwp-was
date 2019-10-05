package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.View;

public interface Controller {
    View service(HttpRequest httpRequest, HttpResponse httpResponse);
}
