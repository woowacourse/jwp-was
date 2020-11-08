package webserver;

import webserver.http.request.HttpRequest;

public abstract class GenericServlet {
    public abstract void doService(HttpRequest httpRequest);
}
