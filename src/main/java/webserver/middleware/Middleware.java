package webserver.middleware;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class Middleware {
    private String path;
    private Middleware nextMiddleware;

    public final Middleware chain(String path, Middleware middleware) {
        this.path = path;
        this.nextMiddleware = middleware;
        return middleware;
    }

    public abstract void service(HttpRequest request, HttpResponse response) throws IOException;

    protected final void next(HttpRequest request, HttpResponse response) throws IOException {
        if (nextMiddleware == null) {
            return;
        }
        if (path == null || request.path().matches(path)) {
            nextMiddleware.service(request, response);
            return;
        }
        nextMiddleware.next(request, response);
    }
}
