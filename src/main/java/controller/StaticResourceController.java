package controller;

import webserver.EntityHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class StaticResourceController extends AbstractController {
    private static final String STATIC_RESOURCE_PREFIX = "./static";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addHeader(EntityHeader.CONTENT_TYPE, "text/css; charset=utf-8");
        httpResponse.forward(STATIC_RESOURCE_PREFIX + httpRequest.getPath());
    }
}
