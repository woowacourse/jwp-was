package controller;

import http.HttpMethod;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class Controller {
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }

        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.METHOD_NOT_ALLOW);
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.METHOD_NOT_ALLOW);
    }
}
