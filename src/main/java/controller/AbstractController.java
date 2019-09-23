package controller;

import http.RequestMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.checkMethod(RequestMethod.GET)) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    abstract void doGet(HttpRequest request, HttpResponse response);

    abstract void doPost(HttpRequest request, HttpResponse response);
}
