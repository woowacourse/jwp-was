package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private final Map<HttpMethod, Controller> mapping = new HashMap<>();

    {
        mapping.put(HttpMethod.GET, this::getMapping);
        mapping.put(HttpMethod.POST, this::postMapping);
    }

    @Override
    public HttpResponse service(HttpRequest request) {
        HttpMethod method = request.method();
        return mapping.get(method).service(request);
    }

    protected HttpResponse getMapping(HttpRequest request) {
        return null;
    }

    protected HttpResponse postMapping(HttpRequest request) {
        return null;
    }
}