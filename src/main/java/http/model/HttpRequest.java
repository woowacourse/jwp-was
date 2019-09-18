package http.model;

import http.supoort.RequestMapping;

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

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }

    public HttpParameters getParameters() {
        return parameters;
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
