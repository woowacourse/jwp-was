package server.http.resource;

import server.http.response.HttpResponse;
import server.http.request.HttpRequest;

public interface ResourceHandler {
    HttpResponse handle(HttpRequest httpRequest);
    boolean canHandle(HttpRequest httpRequest);
}
