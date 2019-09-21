package http.request;

import http.common.HttpVersion;

public class RequestLine {

    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpVersion httpVersion;

    public RequestLine(final HttpMethod httpMethod, final HttpUri httpUri, final HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.httpUri = httpUri;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public String getPath() {
        return httpUri.getPath();
    }

    public String getQuery() {
        return httpUri.getQuery();
    }

    public String findPathPrefix() {
        return httpUri.findPathPrefix();
    }
}
