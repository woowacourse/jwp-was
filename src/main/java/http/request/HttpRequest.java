package http.request;

import http.HttpHeaders;
import http.HttpVersion;

import static http.request.HttpMethod.GET;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private QueryParams queryParams;

    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.queryParams = splitQueryParams();
    }

    private QueryParams splitQueryParams() {
        return GET.equals(requestLine.getHttpMethod())
                ? QueryParams.of(requestLine.getQueryParams())
                : QueryParams.of(body);
    }

    public boolean isStaticContentRequest() {
        HttpUri uri = requestLine.getUri();
        return uri.hasExtension();
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
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
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
