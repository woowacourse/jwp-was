package web.servlet;

import web.HttpRequest;
import web.HttpResponse;

public interface Controller {
    void doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
