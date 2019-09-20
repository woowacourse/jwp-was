package http.response;

import http.request.Request;

public class JsResponse extends AbstractResponse {
    public JsResponse(Request request) {
        super(request);
        contentType = "application/javascript";
    }
}
