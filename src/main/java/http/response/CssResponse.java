package http.response;

import http.request.Request;

public class CssResponse extends AbstractResponse {
    public CssResponse(Request request) {
        super(request);
        contentType = "text/css";
    }
}
