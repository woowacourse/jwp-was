package http.model;

public class HttpRequest {
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpParameters parameters;
    private HttpProtocols httpProtocol;
    private HttpHeaders httpHeaders;
    private HttpSession httpSession;

    public HttpRequest(HttpMethod httpMethod, HttpUri httpUri, HttpProtocols httpProtocol, HttpParameters parameters, HttpHeaders httpHeaders) {
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

    public String getParameter(String key) {
        return parameters.getParameter(key);
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
