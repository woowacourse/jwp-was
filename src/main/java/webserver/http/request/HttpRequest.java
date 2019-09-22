package webserver.http.request;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final Parameter parameter;

    private HttpRequest(final RequestLine requestLine,  final RequestHeaders headers, final Parameter parameter) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.parameter = parameter;
    }

    public static HttpRequest of(final RequestLine requestLine, final RequestHeaders headers, final Parameter parameter) {
        return new HttpRequest(requestLine, headers, parameter);
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
        return parameter.getParameter(key);
    }

    public int sizeOfParameters() {
        return parameter.size();
    }
}