package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpContentType;

public class TestController extends Controller {
    public static HttpResponse test(HttpRequest req) {
        return HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                            .extractFromRequest(req)
                            //.body(pathVars.get("num"))
                            .build();
    }
}