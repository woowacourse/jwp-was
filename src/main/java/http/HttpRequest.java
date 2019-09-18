package http;

public class HttpRequest {
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpProtocol httpProtocol;
    private HttpHeaders headers;

    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    public HttpUri getUri() {
        return httpUri;
    }
}
