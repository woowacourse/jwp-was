package http.request;

import http.common.HttpHeader;
import http.common.HttpVersion;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final HttpHeader requestHeader, final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String findHeader(String name) {
        return requestHeader.findHeader(name);
    }

    public HttpUri getUri() {
        return requestLine.getHttpUri();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getQuery() {
        return requestLine.getQuery();
    }

    public String findParam(String name) {
        return requestBody.findParam(name);
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String findPathPrefix() {
        return requestLine.findPathPrefix();
    }
}
