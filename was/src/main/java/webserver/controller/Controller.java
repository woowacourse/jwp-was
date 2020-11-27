package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.servlet.AbstractView;

public interface Controller {
    AbstractView doService(HttpRequest request, HttpResponse response);
}
