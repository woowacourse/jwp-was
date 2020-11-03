package web.controller;

import web.http.HttpRequest;
import web.http.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
