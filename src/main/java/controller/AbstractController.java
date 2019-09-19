package controller;

import http.HttpResponse;
import http.RequestMethod;
import http.request.HttpRequest;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getMethod().equals(RequestMethod.GET)) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    void doGet(HttpRequest request, HttpResponse response) {
    }

    void doPost(HttpRequest request, HttpResponse response) {
    }
}
