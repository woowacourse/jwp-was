package http;

public class HttpRequestLine {
    private HttpMethod method;
    private HttpUrl url;
    private HttpVersion version;

    public HttpRequestLine(HttpMethod method, HttpUrl url, HttpVersion version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpUrl getUrl() {
        return url;
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "\n" + method + " " + url.getPath() + " " + version;
    }
}
