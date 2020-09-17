package webserver.servlet;

import java.util.Map;

import webserver.domain.request.HttpRequest;

public interface Servlet {
    void service(HttpRequest httpRequest);

    void get(HttpRequest httpRequest);

    void post(HttpRequest httpRequest);

    String getResourcePathToRedirect();
}
