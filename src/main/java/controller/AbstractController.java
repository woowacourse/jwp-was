package controller;

import http.RequestMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.response.view.ErrorView;
import http.response.view.View;

public abstract class AbstractController implements Controller {

    @Override
    public View service(HttpRequest request, HttpResponse response) {
        if (request.checkMethod(RequestMethod.GET)) {
            return doGet(request, response);
        }
        return doPost(request, response);
    }

    View doGet(HttpRequest request, HttpResponse response) {
        return new ErrorView(ResponseStatus.METHOD_NOT_ALLOWED, "Method not allowed");
    }

    View doPost(HttpRequest request, HttpResponse response) {
        return new ErrorView(ResponseStatus.METHOD_NOT_ALLOWED, "Method not allowed");
    }
}