package controller;

import http.RequestMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (RequestMethod.GET == request.getMethod()) {
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
