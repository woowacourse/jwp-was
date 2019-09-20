package http.response;

import http.request.Request;

public class HtmlResponse extends AbstractResponse {
    public HtmlResponse(Request request) {
        super(request);
        contentType = "text/html";
    }
}
