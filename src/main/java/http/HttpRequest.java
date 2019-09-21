package http;

public class HttpRequest {
    private String uri;
    private HttpMethod method;
    private HttpHeader headers;
    private RequestParameter requestParameter;
    private RequestParameter requestBody;
    private String body;

    public HttpRequest(String uri, HttpMethod method, HttpHeader headers, RequestParameter requestParameter, RequestParameter requestBody, String body) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.requestParameter = requestParameter;
        this.requestBody = requestBody;
        this.body = body;
    }

    public static class HttpRequestBuilder {
        private String uri;
        private HttpMethod method;
        private HttpHeader headers;
        private RequestParameter requestParameter;
        private RequestParameter requestBody;
        private String body;

        public HttpRequestBuilder() {
        }

        public HttpRequestBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public HttpRequestBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequestBuilder headers(HttpHeader headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequestBuilder requestParameter(RequestParameter requestParameter) {
            this.requestParameter = requestParameter;
            return this;
        }

        public HttpRequestBuilder requestBody(RequestParameter requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public HttpRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(uri, method, headers, requestParameter, requestBody, body);
        }
    }

    public boolean isStaticRequest() {
        return uri.contains(".");
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHeader(String key) {
        return headers.getValue(key);
    }

    public String getBody() {
        return body;
    }

    public String getParameter(String key) {
        return requestParameter.getParameter(key);
    }

    public String getRequestBody(String key) {
        return requestBody.getParameter(key);
    }
}