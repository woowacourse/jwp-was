package http;

public class HttpRequestLine {

    private final HttpMethod method;
    private final HttpUri uri;
    private final HttpVersion version;

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
        return method.getMethod() + " " +
                uri.toString() + " " +
                version.getVersion() + "\r\n";
    }
}
