package controller;

import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;

public abstract class AbstractController implements Controller {

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
}
