package webserver.middleware;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class Middlewares {
    private final Middleware head;
    private Middleware next;

    public Middlewares() {
        head = new DefaultMiddleware();
        next = head;
    }

    public Middlewares chain(Middleware middleware) {
        return chain(null, middleware);
    }

    public Middlewares chain(String path, Middleware middleware) {
        next = next.chain(path, middleware);
        return this;
    }

    public void run(HttpRequest request, HttpResponse response) throws IOException {
        head.service(request, response);
    }
}
