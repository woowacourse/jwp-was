package controller;

import http.HttpRequest;
import http.HttpResponse;

public interface HttpServlet {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
