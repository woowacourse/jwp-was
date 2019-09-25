package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.http.HttpContentType;

import java.util.Map;

public class TestController {
    public static HttpResponse test(HttpRequest req) {
        return HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                            .extractFromRequest(req)
                            //.body(pathVars.get("num"))
                            .build();
    }
}