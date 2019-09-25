package http.response;

import http.request.HttpRequest;

public class ResponseHandler {
    public static HttpResponse create(HttpRequest httpRequest) {
        return HttpResponse.of(httpRequest.getHttpCookie());
    }
}
