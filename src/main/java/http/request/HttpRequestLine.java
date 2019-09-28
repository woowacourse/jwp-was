package http.request;

import http.HttpVersion;

import static http.HttpString.CRLF;
import static http.HttpString.WHITE_SPACE;

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
        return method.getMethod() + WHITE_SPACE +
                uri.toString() + WHITE_SPACE +
                version.getVersion() + CRLF;
    }
}
