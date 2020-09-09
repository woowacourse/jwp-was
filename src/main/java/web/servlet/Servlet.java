package web.servlet;

import web.HttpRequest;
import web.HttpResponse;

public interface Servlet {
    void doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
