package controller;

import http.Request.Request;
import http.response.Response;

public interface Controller {
    void service(Request request, Response response);
}
