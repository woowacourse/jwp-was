package http.controller;

import http.model.*;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class FileResourceController implements Controller {
    private final static String EXTENSION_SEPARATOR = ".";
    private final static String STATIC_PATH = "./static";
    private final static String TEMPLATES_PATH = "./templates";

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String uri = getViewByUri(httpRequest.getRequestLine().getHttpUri());
        ContentType contentType = getContentType(uri);
        String path = getPath(contentType, uri);

        return new HttpResponse.Builder()
                .forward(path)
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.OK)
                .addHeader(CONTENT_TYPE, contentType.getType())
                .build();
    }

    private String getViewByUri(HttpUri uri) {
        return uri.getResourceLocation();
    }

    private ContentType getContentType(String uri) {
        String extension = uri.substring(uri.lastIndexOf(EXTENSION_SEPARATOR) + 1);
        return ContentType.of(extension);
    }

    private String getPath(ContentType contentType, String uri) {
        if (ContentType.HTML.equals(contentType)) {
            return TEMPLATES_PATH + uri;
        }
        return STATIC_PATH + uri;
    }
}