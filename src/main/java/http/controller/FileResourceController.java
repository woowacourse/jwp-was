package http.controller;

import http.model.HttpRequest;
import http.model.HttpResponse;
import http.model.HttpUri;

public class FileResourceController implements Controller {
    private final static String STATIC_PATH = "./static";
    private final static String TEMPLATES_PATH = "./templates";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        String uri = getViewByUri(httpRequest.getRequestLine().getHttpUri());
        String path = getPath(uri);

        return new HttpResponse.Builder()
                .forward(path)
                .build();
    }

    private String getViewByUri(HttpUri uri) {
        return uri.getResourceLocation();
    }

    private String getPath(String uri) {
        if (uri.endsWith(".html")) {
            return TEMPLATES_PATH + uri;
        }
        return STATIC_PATH + uri;
    }
}