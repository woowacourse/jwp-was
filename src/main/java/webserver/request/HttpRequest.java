package webserver.request;

import webserver.request.requestline.HttpMethod;
import webserver.request.requestline.HttpRequestLine;
import webserver.request.requestline.QueryParams;

public class HttpRequest {

    private HttpRequestLine httpRequestLine;
    private HttpHeaderFields httpHeaderFields;

    public HttpRequest(final HttpRequestLine httpRequestLine, final HttpHeaderFields httpHeaderFields) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaderFields = httpHeaderFields;
    }

    public HttpMethod findMethod() {
        return httpRequestLine.getMethod();
    }

    public String findFilePath() {
        return httpRequestLine.findFilePath();
    }

    public String findContentType() {
        return httpRequestLine.findContentType();
    }

    public QueryParams findQueryParams() {
        return httpRequestLine.findQueryParams();
    }
}
