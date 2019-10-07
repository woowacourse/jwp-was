package http.application.controller;

import http.application.Controller;
import http.exception.NotSupportHttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        switch (httpRequest.getHttpMethod()) {
            case GET:
                doGet(httpRequest, httpResponse);
                return;
            case POST:
                doPost(httpRequest, httpResponse);
                return;
            default:
                throw new NotSupportHttpMethodException();
        }
    }

    abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
