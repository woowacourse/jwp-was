package http.response;

import http.common.HttpHeader;

public class HttpResponse {

    private StatusLine statusLine;
    private HttpHeader responseHeader;
    private ResponseBody responseBody;

    public HttpResponse(final StatusLine statusLine, final HttpHeader responseHeader, final ResponseBody responseBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }
}
