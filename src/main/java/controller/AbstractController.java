package controller;

import http.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }

        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
}
