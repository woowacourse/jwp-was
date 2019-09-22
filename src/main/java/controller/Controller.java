package controller;

import http.request.Request2;
import http.response.Response2;

public interface Controller {

    boolean isMapping(Request2 request);
    Response2 createResponse(Request2 request);
}
