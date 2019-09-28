package http;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpHeader header;
    private final HttpBody body;

    public HttpRequest(HttpRequestLine requestLine, HttpHeader header, HttpBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static class HttpRequestBuilder {
        private HttpRequestLine requestLine;
        private HttpHeader header;
        private HttpBody body;

        public HttpRequestBuilder() { }

        public HttpRequestBuilder requestLine(HttpRequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public HttpRequestBuilder header(HttpHeader header) {
            this.header = header;
            return this;
        }

        public HttpRequestBuilder body(HttpBody body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(requestLine, header, body);
        }
    }

    public boolean isStaticRequest() {
        return requestLine.getUri().isFileUri();
    }

    public String getUri() {
        return requestLine.getUri().toString();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getHeaderAttribute(String key) {
        return header.getValue(key);
    }
}
