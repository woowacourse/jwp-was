package web.controller;

import web.HttpRequest;
import web.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
