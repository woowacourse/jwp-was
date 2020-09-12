package http.request;

public class RequestLine {

    private String method;
    private String uri;
    private String version;

    public RequestLine(String method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public String getUri() {
        return uri;
    }
}
