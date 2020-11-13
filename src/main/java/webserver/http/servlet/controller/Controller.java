package webserver.http.servlet.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.view.View;

public interface Controller {
    View doService(HttpRequest request, HttpResponse response);
}
