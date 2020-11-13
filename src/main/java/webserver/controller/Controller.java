package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.servlet.View;

public interface Controller {
    View doService(HttpRequest request, HttpResponse response);
}
