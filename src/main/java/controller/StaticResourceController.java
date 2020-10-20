package controller;

import webserver.ContentType;
import webserver.EntityHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class StaticResourceController extends AbstractController {
    private static final String STATIC_RESOURCE_PREFIX = "./static";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ContentType contentType = ContentType.form(httpRequest.getPath());
        httpResponse.addHeader(EntityHeader.CONTENT_TYPE, contentType.get());
        httpResponse.forward(STATIC_RESOURCE_PREFIX + httpRequest.getPath());
    }
}
