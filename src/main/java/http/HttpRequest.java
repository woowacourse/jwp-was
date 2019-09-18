package http;

public class HttpRequest {
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpParameters parameters;
    private HttpProtocol httpProtocol;
    private HttpHeaders headers;

    public HttpRequest(HttpMethod method, HttpUri uri, HttpParameters httpParameters, HttpProtocol protocol) {
        httpMethod = method;
        httpUri = uri;
        parameters = httpParameters;
        httpProtocol = protocol;
    }

    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    public HttpUri getUri() {
        return httpUri;
    }

    public RequestMapping getMapping() {
        return new RequestMapping(httpMethod, httpUri);
    }
}
