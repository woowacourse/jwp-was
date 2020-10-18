package controller;

import java.util.HashSet;
import java.util.Set;

import http.request.HttpRequest;
import http.request.HttpRequestMapping;

public abstract class HttpRequestMappingAbstractController implements Controller {

    private final Set<HttpRequestMapping> httpRequestMappings = new HashSet<>();

    public HttpRequestMappingAbstractController(HttpRequestMapping httpRequestMapping) {
        httpRequestMappings.add(httpRequestMapping);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return httpRequestMappings.stream()
            .anyMatch(httpRequestMapping -> httpRequestMapping.match(httpRequest));
    }
}
