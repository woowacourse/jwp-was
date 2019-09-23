package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpContentType;

import java.util.Map;

public class TestController {
    public static HttpResponse test(HttpRequest req, Map<String, String> pathVars) {
        return HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                            .version(req)
                            .connection(req)
                            .body(pathVars.get("num"))
                            .build();
    }
}