package server.http.request;

import java.util.Map;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Map<String, String> headers;
    private final Map<String, String> body;
    private final Map<String, String> cookies;

    public HttpRequest(final HttpRequestLine requestLine, Map<String, String> cookies, final Map<String, String> headers, final Map<String, String> body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookies = cookies;
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

    public String getCookie(final String key) { return cookies.get(key); }
}