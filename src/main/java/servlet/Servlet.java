package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Servlet {
    void handle(HttpRequest request, HttpResponse response);
}
