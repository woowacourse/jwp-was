package controller;

import http.RequestMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.ResponseStatus;
import http.response.view.ErrorView;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.checkMethod(RequestMethod.GET)) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    void doGet(HttpRequest request, HttpResponse response) {
        resolveMethodNotFound(response);
    }


    void doPost(HttpRequest request, HttpResponse response) {
        resolveMethodNotFound(response);
    }


    private void resolveMethodNotFound(HttpResponse response) {
        ResponseResolver.resolve(new ErrorView(ResponseStatus.METHOD_NOT_ALLOWED, "Method not allowed"), response);
    }
}