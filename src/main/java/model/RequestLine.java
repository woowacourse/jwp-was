package model;

public class RequestLine {
    private final HttpMethod method;
    private final RequestURI uri;
    private final HttpVersion version;

    public RequestLine(HttpMethod method, RequestURI uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public RequestURI getUri() {
        return uri;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
