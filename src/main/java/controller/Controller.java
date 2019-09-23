package controller;

import http.request.Request;
import http.response.Response;

public interface Controller {

    boolean isMapping(ControllerMapper controllerMapper);

    Response createResponse(Request request);
}
