package http.response;

import http.request.HttpRequest;

public class ResponseHandler {
    private static class ResponseHandlerHolder {
        private static final ResponseHandler instance = new ResponseHandler();
    }

    private ResponseHandler() {
    }

    public static ResponseHandler getInstance() {
        return ResponseHandlerHolder.instance;
    }

    public HttpResponse create(HttpRequest httpRequest) {
        return HttpResponse.of(httpRequest.getHttpCookie());
    }
}
