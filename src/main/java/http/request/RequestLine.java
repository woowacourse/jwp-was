package http.request;

import http.common.HttpVersion;

import java.util.Objects;

public class RequestLine {
    private final Url url;
    private final HttpMethod httpMethod;
    private final HttpVersion httpVersion;

    public RequestLine(final Url url, final HttpMethod httpMethod, final HttpVersion httpVersion) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(url, that.url) &&
                httpMethod == that.httpMethod &&
                httpVersion == that.httpVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, httpMethod, httpVersion);
    }
}
