package http.model;

public class HttpRequest {
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpParameters parameters;
    private HttpProtocols httpProtocol;
    private HttpHeaders httpHeaders;

    public HttpRequest(HttpMethod httpMethod, HttpUri httpUri, HttpParameters parameters, HttpProtocols httpProtocol, HttpHeaders httpHeaders) {
        this.httpMethod = httpMethod;
        this.httpUri = httpUri;
        this.parameters = parameters;
        this.httpProtocol = httpProtocol;
        this.httpHeaders = httpHeaders;
    }

    public HttpUri getUri() {
        return httpUri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }

    public HttpParameters getParameters() {
        return parameters;
    }

    public HttpProtocols getHttpProtocol() {
        return httpProtocol;
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }
}
