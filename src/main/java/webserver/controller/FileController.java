package webserver.controller;

import http.HttpResponse;
import http.request.HttpRequest;
import http.support.resource.ResourceType;
import http.support.resource.ResourceTypeFactory;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends HttpController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String absoluteUrl = PathHandler.path(httpRequest.getResourcePath());
        ResourceType resourceType = ResourceTypeFactory.getInstance(absoluteUrl);
        httpResponse.addHeader("Content-Type", resourceType.getResourceType());
        httpResponse.forward(absoluteUrl);
    }
}
