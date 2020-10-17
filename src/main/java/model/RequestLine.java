package model;

public class RequestLine {
    private final HttpMethod method;
    private final RequestURI requestURI;
    private final HttpVersion version;

    public RequestLine(HttpMethod method, RequestURI requestURI, HttpVersion version) {
        this.method = method;
        this.requestURI = requestURI;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public RequestURI getRequestURI() {
        return requestURI;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
