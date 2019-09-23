package http.response;

import http.common.HttpHeader;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse(final StatusLine statusLine,
                        final HttpHeader httpHeader,
                        final HttpResponseBody httpResponseBody) {
        this.statusLine = statusLine;
        this.httpHeader = httpHeader;
        this.httpResponseBody = httpResponseBody;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusLine=" + statusLine +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                '}';
    }
}
