package http.request;

import http.HttpHeaders;
import http.HttpVersion;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private QueryParams queryParams;

    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public boolean isStaticContentRequest() {
        HttpUri uri = requestLine.getUri();
        return uri.hasExtension();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        HttpUri uri = requestLine.getUri();
        return uri.getPath();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public QueryParams getQueryParams() {
        if (HttpMethod.GET.match(requestLine.getMethod())) {
            return QueryParams.of(requestLine.getUri().getQueryParams());
        }
        return QueryParams.of(body);
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
