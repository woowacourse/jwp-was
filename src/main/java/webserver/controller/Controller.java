package webserver.controller;

import java.io.IOException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.middleware.Middleware;

public abstract class Controller extends Middleware {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        switch (request.method()) {
            case GET:
                doGet(request, response);
                return;
            case POST:
                doPost(request, response);
                return;
        }
        next(request, response);
    }

    protected abstract void doGet(HttpRequest request, HttpResponse response) throws IOException;

    protected abstract void doPost(HttpRequest request, HttpResponse response) throws IOException;
}
