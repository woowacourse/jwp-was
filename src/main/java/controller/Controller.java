package controller;

import http.request.Request;
import http.response.Response;

public interface Controller {
    void service(Request request, Response response);
}
