package http;

public class RequestLine {
    private RequestMethod method;

    private String url;

    private String protocol;

    public RequestLine(RequestMethod method, String url, String protocol) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }
}
