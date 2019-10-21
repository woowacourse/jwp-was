package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.session.Cookie;

public class HttpRequestBuilder {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private Cookie cookie;
    private QueryParams queryParams;

    public HttpRequestBuilder requestLine(HttpMethod method, HttpUri path, HttpVersion version) {
        requestLine = new HttpRequestLine(method, path, version);
        return this;
    }

    public HttpRequestBuilder requestLine(HttpRequestLine requestLine) {
        this.requestLine = requestLine;
        return this;
    }

    public HttpRequestBuilder headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public HttpRequestBuilder queryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public HttpRequestBuilder cookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    public HttpRequest build() {
        return new HttpRequest(requestLine, headers, body, queryParams, cookie);
    }
}
