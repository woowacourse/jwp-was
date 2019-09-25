package http.request;

import http.HttpRequestMethod;
import http.HttpVersion;

import java.util.Objects;

public class HttpRequestStartLine {
    private HttpRequestMethod httpRequestMethod;
    private HttpRequestTarget httpRequestTarget;
    private HttpVersion httpVersion;

    public HttpRequestStartLine(HttpRequestMethod httpRequestMethod, HttpRequestTarget httpRequestTarget, HttpVersion httpVersion) {
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestTarget = httpRequestTarget;
        this.httpVersion = httpVersion;
    }

    public boolean hasBody() {
        return httpRequestMethod.hasBody();
    }

    public String getQueryString() {
        return httpRequestTarget.getQueryString();
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public String getUri() {
        return httpRequestTarget.getUri();
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestStartLine that = (HttpRequestStartLine) o;
        return httpRequestMethod == that.httpRequestMethod &&
                httpRequestTarget.equals(that.httpRequestTarget) &&
                httpVersion == that.httpVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpRequestMethod, httpRequestTarget, httpVersion);
    }

    @Override
    public String toString() {
        return "HttpRequestStartLine{" +
                "httpRequestMethod=" + httpRequestMethod +
                ", httpRequestTarget=" + httpRequestTarget +
                ", httpVersion=" + httpVersion +
                '}';
    }
}
