package controller;

import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;

abstract public class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        return HttpResponse.of(Status.METHOD_NOT_ALLOWED);
    }

    public HttpResponse doGet(HttpRequest request) {
        return HttpResponse.of(Status.METHOD_NOT_ALLOWED);
    }

    public HttpResponse doPost(HttpRequest request) {
        return HttpResponse.of(Status.METHOD_NOT_ALLOWED);
    }

    public HttpResponse doPut(HttpRequest request) {
        return HttpResponse.of(Status.METHOD_NOT_ALLOWED);
    }

    public HttpResponse doDelete(HttpRequest request) {
        return HttpResponse.of(Status.METHOD_NOT_ALLOWED);
    }
}
