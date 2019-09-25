package webserver.http.request;

import webserver.http.common.HttpHeader;

public class HttpRequest {
    private RequestLine requestLine;
    private HttpHeader httpHeader;
    private QueryStringParams queryStringParams;

    public void init(final RequestLine requestLine,
                       final HttpHeader httpHeader,
                       final QueryStringParams queryStringParams) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.queryStringParams = queryStringParams;
    }

    public void addHeader(final String key, final String value) {
        httpHeader.put(key, value);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public QueryStringParams getQueryStringParams() {
        return queryStringParams;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", httpHeader=" + httpHeader +
                ", httpRequestParams=" + queryStringParams +
                '}';
    }
}