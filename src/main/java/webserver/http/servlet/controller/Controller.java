package webserver.http.servlet.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractView;

public interface Controller {
    AbstractView doService(HttpRequest request, HttpResponse response);
}
