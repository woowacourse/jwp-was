package controller;

import request.HttpRequest;
import request.Method;
import response.HttpResponse;
import response.StatusCode;

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
        return new HttpResponse(StatusCode.NOT_FOUND);
    }

    abstract protected HttpResponse doGet(HttpRequest httpRequest);

    abstract protected HttpResponse doPost(HttpRequest httpRequest);

    abstract protected HttpResponse doPut(HttpRequest httpRequest);

    abstract protected HttpResponse doDelete(HttpRequest httpRequest);
}
