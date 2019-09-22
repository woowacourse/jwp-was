package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.Http404ResponseEntity;
import http.response.HttpResponseEntity;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponseEntity handle(HttpRequest httpRequest) {
        if (HttpMethod.GET.match(httpRequest.getMethod())) {
            return doGet(httpRequest);
        }
        if (HttpMethod.POST.match(httpRequest.getMethod())) {
            return doPost(httpRequest);
        }
        return new Http404ResponseEntity("/error.html");
    }

    protected HttpResponseEntity doGet(HttpRequest httpRequest) {
        return new Http404ResponseEntity("/error.html");
    }

    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        return new Http404ResponseEntity("/error.html");
    }
}
