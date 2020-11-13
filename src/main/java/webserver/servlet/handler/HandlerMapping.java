package webserver.servlet.handler;

import webserver.servlet.http.request.HttpRequest;
import webserver.servlet.http.response.HttpResponse;

public interface HandlerMapping {

    boolean isSupport(HttpRequest request);

    void handle(HttpRequest request, HttpResponse response);
}
