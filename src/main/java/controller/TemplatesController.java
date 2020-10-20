package controller;

import webserver.ContentType;
import webserver.EntityHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class TemplatesController extends AbstractController {
    private static final String PAGE_PREFIX = "./templates";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ContentType contentType = ContentType.form(httpRequest.getPath());
        httpResponse.addHeader(EntityHeader.CONTENT_TYPE, contentType.get());
        httpResponse.forward(PAGE_PREFIX + httpRequest.getPath());
    }
}
