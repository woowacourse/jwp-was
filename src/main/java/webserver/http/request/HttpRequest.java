package webserver.http.request;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final Parameters parameters;

    private HttpRequest(final RequestLine requestLine, final RequestHeaders headers, final Parameters parameters) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameters = parameters;
    }

    public static HttpRequest of(final RequestLine requestLine, final RequestHeaders headers, final Parameters parameters) {
        return new HttpRequest(requestLine, headers, parameters);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getHeader(final String name) {
        return headers.getHeader(name);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpVersion getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getParameter(final String key) {
        return parameters.getParameter(key);
    }

    public int sizeOfParameters() {
        return parameters.size();
    }
}