package http.application;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Service {
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
