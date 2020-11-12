package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface HandlerMapping {

    boolean isSupport(HttpRequest request);

    void handle(HttpRequest request, HttpResponse response);
}
