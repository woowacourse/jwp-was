package controller;

import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.Status;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isMatchHttpMethod(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.isMatchHttpMethod(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
            return;
        }
        httpResponse.sendError(Status.METHOD_NOT_ALLOWED);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendError(Status.METHOD_NOT_ALLOWED);
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendError(Status.METHOD_NOT_ALLOWED);
    }
}