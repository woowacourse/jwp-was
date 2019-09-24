package http.application;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
