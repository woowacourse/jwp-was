package controller;

import http.request.Request;
import http.response.Response;
import http.support.HttpMethod;
import http.support.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            doPost(request, response);
            return;
        }

        if (request.getMethod().equals(HttpMethod.GET.name())) {
            doGet(request, response);
        }

        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    public void doPost(Request request, Response response) {
        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    public void doGet(Request request, Response response) {
        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
