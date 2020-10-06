package http.controller;

import http.HttpRequest;
import http.HttpResponse;
import utils.HttpResponseHeaderParser;

public abstract class Controller {
    public HttpResponse get(HttpRequest httpRequest) {
        String header = HttpResponseHeaderParser.methodNotAllowed();
        return new HttpResponse(header);
    }

    public HttpResponse post(HttpRequest httpRequest) {
        String header = HttpResponseHeaderParser.methodNotAllowed();
        return new HttpResponse(header);
    }
}
