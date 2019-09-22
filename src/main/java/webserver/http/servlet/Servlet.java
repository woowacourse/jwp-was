package webserver.http.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Servlet {
    void service(final HttpRequest httpRequest, final HttpResponse httpResponse);
}