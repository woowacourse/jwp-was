package webserver.middleware;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class DefaultMiddleware extends Middleware {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        next(request, response);
    }
}
