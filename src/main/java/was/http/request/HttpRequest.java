package was.http.request;

import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> body;

    public HttpRequest(final HttpRequestLine requestLine, final Map<String, String> headers, final Map<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(final String key) {
        return headers.get(key);
    }

    public String getParam(final String key) {
        return requestLine.getQueryParam(key);
    }

    public String getBody(final String key) {
        return body.get(key);
    }
}