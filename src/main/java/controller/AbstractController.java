package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponseEntity handle(HttpRequest httpRequest) {
        if (httpRequest.getMethod().match(HttpMethod.GET)) {
            return doGet(httpRequest);
        }
        if (httpRequest.getMethod().match(HttpMethod.POST)) {
            return doPost(httpRequest);
        }
        return HttpResponseEntity.get404Response();
    }

    protected HttpResponseEntity doGet(HttpRequest httpRequest) {
        return HttpResponseEntity.get404Response();
    }

    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        return HttpResponseEntity.get404Response();
    }
}
