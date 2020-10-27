package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
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
