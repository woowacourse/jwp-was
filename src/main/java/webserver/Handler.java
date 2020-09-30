package webserver;

import http.HttpRequest;
import http.HttpResponse;

public interface Handler {
    void handleRequest(final HttpRequest request, final HttpResponse response) throws Exception;
}
