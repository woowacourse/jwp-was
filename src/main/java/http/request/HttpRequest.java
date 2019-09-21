package http.request;

import http.common.HttpHeader;

public class HttpRequest {
    private RequestLine requestLine;
    private HttpHeader httpHeader;
    private HttpRequestParams httpRequestParams;

    public HttpRequest(final RequestLine requestLine,
                       final HttpHeader httpHeader,
                       final HttpRequestParams httpRequestParams) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.httpRequestParams = httpRequestParams;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpRequestParams getHttpRequestParams() {
        return httpRequestParams;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", httpHeader=" + httpHeader +
                ", httpRequestParams=" + httpRequestParams +
                '}';
    }
}