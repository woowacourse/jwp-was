package http.model;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final HttpProtocols httpProtocols;
    private final HttpUri httpUri;

    public RequestLine(HttpMethod httpMethod, HttpProtocols httpProtocols, HttpUri httpUri) {
        this.httpMethod = httpMethod;
        this.httpProtocols = httpProtocols;
        this.httpUri = httpUri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpProtocols getHttpProtocols() {
        return httpProtocols;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }
}
