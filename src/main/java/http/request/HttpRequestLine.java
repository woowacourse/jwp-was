package http.request;

public class HttpRequestLine {

    private String method;
    private String uri;
    private String version;

    public HttpRequestLine(String method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public String getUri() {
        return uri;
    }
}
