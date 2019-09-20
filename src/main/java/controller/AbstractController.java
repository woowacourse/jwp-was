package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import http.response.HttpStatus;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponseEntity handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod().match(HttpMethod.GET)) {
            return doGet(httpRequest);
        }

        if (httpRequest.getMethod().match(HttpMethod.POST)) {
            return doPost(httpRequest);
        }
        return new HttpResponseEntity("error.html", HttpStatus.NOT_FOUND);
    }

    protected HttpResponseEntity doGet(HttpRequest httpRequest) {
        return new HttpResponseEntity("error.html", HttpStatus.NOT_FOUND);
    }

    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        return new HttpResponseEntity("error.html", HttpStatus.NOT_FOUND);
    }
}
