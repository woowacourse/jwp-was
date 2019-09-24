package http;

import webserver.exception.InvalidUriException;

public class HttpStartLine {
    private String uri;
    private HttpMethod method;
    private RequestParameter requestParameter;

    public HttpStartLine(String uri, HttpMethod method, RequestParameter requestParameter) {
        validateNull(uri);

        this.uri = uri;
        this.method = method;
        this.requestParameter = requestParameter;
    }

    private void validateNull(String uri) {
        if (uri == null) {
            throw new InvalidUriException();
        }
    }

    public HttpStartLine(String uri, HttpMethod method) {
        this(uri, method, null);
    }

    public boolean matchMethod(HttpMethod method) {
        return this.method.equals(method);
    }

    public boolean isStaticUri() {
        return uri.contains(".");
    }

    public String getParameter(String key) {
        return requestParameter.getParameter(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }
}
