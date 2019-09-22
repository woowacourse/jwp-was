package http.request;

import http.common.HttpVersion;

public class RequestLine {
    private Url url;
    private HttpMethod httpMethod;
    private HttpVersion httpVersion;

    public RequestLine(Url url, HttpMethod httpMethod, HttpVersion httpVersion) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpVersion = httpVersion;
    }

    public Url getUrl() {
        return url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "url=" + url +
                ", httpMethod=" + httpMethod +
                ", httpVersion=" + httpVersion +
                '}';
    }
}
