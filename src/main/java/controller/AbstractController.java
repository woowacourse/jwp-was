package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractController implements Controller {

    private Map<HttpMethod, Function<HttpRequest, HttpResponse>> methodFinder = new HashMap<>();

    AbstractController() {
        methodFinder.put(HttpMethod.GET, this::doGet);
        methodFinder.put(HttpMethod.POST, this::doPost);
        methodFinder.put(HttpMethod.PUT, this::doPut);
        methodFinder.put(HttpMethod.DELETE, this::doDelete);
    }

    public HttpResponse service(HttpRequest httpRequest) {
        HttpMethod httpMethod = httpRequest.getHttpMethod();
        return methodFinder.get(httpMethod).apply(httpRequest);
    }

    abstract HttpResponse doGet(HttpRequest httpRequest);

    abstract HttpResponse doPost(HttpRequest httpRequest);

    abstract HttpResponse doPut(HttpRequest httpRequest);

    abstract HttpResponse doDelete(HttpRequest httpRequest);
}
