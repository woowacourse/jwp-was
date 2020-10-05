package controller;

import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isMatchHttpMethod(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
        }
        if (httpRequest.isMatchHttpMethod(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
        }
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

}