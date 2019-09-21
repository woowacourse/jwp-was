package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.response_entity.Http404ResponseEntity;
import http.response.response_entity.HttpResponseEntity;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponseEntity handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod().match(HttpMethod.GET)) {
            return doGet(httpRequest);
        }
        if (httpRequest.getMethod().match(HttpMethod.POST)) {
            return doPost(httpRequest);
        }
        return new Http404ResponseEntity();
    }

    protected HttpResponseEntity doGet(HttpRequest httpRequest) {
        return new Http404ResponseEntity();
    }

    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        return new Http404ResponseEntity();
    }
}
