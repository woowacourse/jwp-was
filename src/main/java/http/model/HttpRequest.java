package http.model;

import java.util.Map;

public class HttpRequest {
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpParameters parameters;
    private HttpProtocols httpProtocol;
    private Map<String, String> headers;

    public HttpRequest(HttpMethod method, HttpUri uri, HttpParameters httpParameters, HttpProtocols protocol, Map<String, String> headers) {
        httpMethod = method;
        httpUri = uri;
        parameters = httpParameters;
        httpProtocol = protocol;
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return HttpMethod.GET;
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

    public Map<String, String> getHeaders() {
        return headers;
    }
}
