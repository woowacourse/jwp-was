package http.request;

import http.HttpHeaders;
import http.HttpMIMEType;
import http.HttpVersion;
import utils.ExtensionParser;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;

    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public HttpUri getUri() {
        return requestLine.getUri();
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

    public HttpMIMEType getMIMEType() {
        String accept = headers.getHeader("Accept");
        String extension = ExtensionParser.parse(requestLine.getUri().getPath());
        return HttpMIMEType.getMIMETypeFrom(accept, extension);
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
