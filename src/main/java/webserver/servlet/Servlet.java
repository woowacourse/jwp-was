package webserver.servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Servlet {
    static final String TEMPLATE_PATH = "./templates";

    void doService(HttpRequest request, HttpResponse response);
}
