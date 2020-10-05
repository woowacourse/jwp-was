package http.controller;

import http.HttpRequest;
import http.HttpResponse;
import utils.HttpResponseHeaderParser;

public class IndexController extends Controller {
    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return new HttpResponse(HttpResponseHeaderParser.found("/index.html"));
    }
}
