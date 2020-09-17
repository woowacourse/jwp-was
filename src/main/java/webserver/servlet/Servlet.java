package webserver.servlet;

import java.util.Map;

import webserver.domain.request.HttpRequest;

public interface Servlet {
    void service(HttpRequest httpRequest);
    String getResourcePathToRedirect();
}
