package webserver.http.handler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface ResourceHandler {
    void handle(final HttpRequest httpRequest, final HttpResponse httpResponse);

    boolean isMapping(final String extension);
}
