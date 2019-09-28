package http.request;

import http.HttpVersion;

public class HttpRequestLine {

    private final HttpRequestMethod method;
    private final HttpRequestUri uri;
    private final HttpVersion version;

    public HttpRequestLine(HttpRequestMethod method, HttpRequestUri uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public HttpRequestMethod getMethod() {
        return method;
    }

    public HttpRequestUri getUri() {
        return uri;
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return method.getMethod() + " " +
                uri.toString() + " " +
                version.getVersion() + "\r\n";
    }
}
