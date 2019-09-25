package controller;

import http.request.Request;
import http.response.Response;
import http.response.Response2;

public interface Controller {

    boolean isMapping(ControllerMapper controllerMapper);

    void processResponse(Request request, Response2 response);
}
