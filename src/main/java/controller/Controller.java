package controller;

import http.request.Request;
import http.response.Response;

public interface Controller {

    boolean isMapping(Request request);
    Response createResponse(Request request);
}
