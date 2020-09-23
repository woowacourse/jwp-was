package webserver.servlet;

import webserver.domain.request.HttpRequest;

public interface Servlet {
    void service(HttpRequest httpRequest);

    void get(HttpRequest httpRequest);

    void post(HttpRequest httpRequest);
}
