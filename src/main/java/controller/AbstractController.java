package controller;

import request.HttpRequest;
import request.Method;
import response.HttpResponse;

abstract public class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        if (httpRequest.isMethod(Method.GET)) {
            return doGet(httpRequest);
        }
        if (httpRequest.isMethod(Method.POST)) {
            return doPost(httpRequest);
        }
        if (httpRequest.isMethod(Method.PUT)) {
            return doPut(httpRequest);
        }
        if (httpRequest.isMethod(Method.DELETE)) {
            return doDelete(httpRequest);
        }
        throw new IllegalArgumentException("http request method not a predefined http method.");
    }

    abstract public HttpResponse doGet(HttpRequest httpRequest);

    abstract public HttpResponse doPost(HttpRequest httpRequest);

    abstract public HttpResponse doPut(HttpRequest httpRequest);

    abstract public HttpResponse doDelete(HttpRequest httpRequest);
}
