package http.request;

import http.common.HttpHeader;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpRequestParams httpRequestParams;
    private final HttpHeader httpHeader;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(final RequestLine requestLine,
                       final HttpRequestParams httpRequestParams,
                       final HttpHeader httpHeader,
                       final HttpRequestBody httpRequestBody) {
        this.requestLine = requestLine;
        this.httpRequestParams = httpRequestParams;
        this.httpHeader = httpHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpRequestParams getHttpRequestParams() {
        return httpRequestParams;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }
}