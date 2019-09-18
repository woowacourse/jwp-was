package webserver.request;

import webserver.request.requestline.HttpMethod;
import webserver.request.requestline.HttpRequestLine;

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
}
