package webserver.httphandler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface HttpRequestHandler {
    default boolean canHandle(String path) {
        return false;
    }

    void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse);
}
