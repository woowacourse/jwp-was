package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.HttpResponseHeaderParser;

public class IndexController extends Controller {
    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return new HttpResponse(HttpResponseHeaderParser.found("/index.html"));
    }
}
