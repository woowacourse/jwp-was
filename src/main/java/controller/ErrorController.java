package controller;

import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;

public class ErrorController extends AbstractController {

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        return HttpResponse.of(Status.NOT_FOUND);
    }
}
