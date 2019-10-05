package http.request;

import http.HttpVersion;

public class HttpRequestLine {

    private final HttpMethod method;
    private final HttpUri uri;
    private final HttpVersion version;

    public HttpRequestLine(HttpMethod method, HttpUri uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public boolean includeFileUri() {
        return uri.isFileUri();
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
        return String.format("%s %s %s\r\n", method.getMethod(),
                uri.toString(), version.getVersion());
    }
}
