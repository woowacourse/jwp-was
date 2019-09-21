package http;

public class RequestLine {

    private HttpMethod method;
    private HttpUri uri;
    private HttpVersion version;

    public RequestLine(final HttpMethod method, final HttpUri uri, final HttpVersion version) {
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
}
