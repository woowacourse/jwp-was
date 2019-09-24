package http;

public class HttpRequest {
    private HttpStartLine startLine;
    private HttpHeader headers;
    private HttpBody body;

    private HttpRequest(HttpStartLine startLine, HttpHeader headers, HttpBody body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public static class HttpRequestBuilder {
        private HttpStartLine startLine;
        private HttpHeader headers;
        private HttpBody body;

        public HttpRequestBuilder startLine(HttpStartLine startLine) {
            this.startLine = startLine;
            return this;
        }

        public HttpRequestBuilder headers(HttpHeader headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequestBuilder body(HttpBody body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(startLine, headers, body);
        }
    }

    public boolean isStaticRequest() {
        return startLine.isStaticUri();
    }

    public boolean matchMethod(HttpMethod method) {
        return startLine.matchMethod(method);
    }

    public String getUri() {
        return startLine.getUri();
    }

    public HttpMethod getMethod() {
        return startLine.getMethod();
    }

    public String getHeader(String key) {
        return headers.getValue(key);
    }

    public String getBody() {
        return (body == null) ? null : body.getBody();
    }

    public String getParameter(String key) {
        return startLine.getParameter(key);
    }

    public String getRequestBody(String key) {
        return (body == null) ? null : body.getParameter(key);
    }
}