package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpContentType;
import webserver.httpelement.HttpStatusCode;

public abstract class Controller {
    protected static HttpResponse redirectTo(HttpRequest req, String redirectPath) {
        return HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                            .extractFromRequest(req)
                            .statusCode(HttpStatusCode.FOUND)
                            .location(redirectPath)
                            .build();
    }
}