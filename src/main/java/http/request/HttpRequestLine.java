package http.request;

import http.HttpVersion;

public class HttpRequestLine {
    private HttpMethod method;
    private HttpUri uri;
    private HttpVersion version;

    public HttpRequestLine(HttpMethod method, HttpUri uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpUri getUri() {
        return uri;
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "\n" + method + " " + uri.getPath() + " " + version;
    }
}
