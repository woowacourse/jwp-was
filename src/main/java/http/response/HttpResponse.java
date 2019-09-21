package http.response;

import http.common.HttpHeader;
import http.common.HttpVersion;

public class HttpResponse {
    private StartLine startLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse(final StartLine startLine, final HttpHeader httpHeader, final HttpResponseBody httpResponseBody) {
        this.startLine = startLine;
        this.httpHeader = httpHeader;
        this.httpResponseBody = httpResponseBody;
    }

    public StartLine getStartLine() {
        return startLine;
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
                "startLine=" + startLine +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                '}';
    }
}
