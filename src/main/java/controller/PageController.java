package controller;

import webserver.EntityHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class PageController extends AbstractController {
    private static final String PAGE_PREFIX = "./templates";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addHeader(EntityHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        httpResponse.forward(PAGE_PREFIX + httpRequest.getPath());
    }
}
