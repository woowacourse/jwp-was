package controller;

import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;
import view.View;

public class ResourceController extends HttpRequestMappingAbstractController {

    public ResourceController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.ok(new View(httpRequest.getRequestLine().getPath()));
    }
}
