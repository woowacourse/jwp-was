package webserver.httprequesthandler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface HttpRequestHandler {
    boolean canHandle(String path);

    void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
