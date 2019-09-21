package http;

import java.util.Map;

public class HttpRequest2 {
    private final HttpRequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> body;

    public HttpRequest2(HttpRequestLine requestLine, Map<String, String> headers, Map<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpRequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getBody() {
        return body;
    }
}
