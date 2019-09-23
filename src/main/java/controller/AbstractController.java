package controller;

import http.support.HttpMethod;
import http.Request.Request;
import http.response.Response;
import http.support.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.getMethod().contains(HttpMethod.POST.name())) {
            doPost(request, response);
        }
        if (request.getMethod().contains(HttpMethod.GET.name())) {
            doGet(request, response);
        }
    }

    public void doPost(Request request, Response response) {
        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    public void doGet(Request request, Response response) {
        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
