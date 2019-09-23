package webserver.request;

import webserver.request.requestline.HttpMethod;
import webserver.request.requestline.HttpRequestLine;
import webserver.request.requestline.QueryParams;

public class HttpRequest {

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeaderFields httpRequestHeaderFields;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(final HttpRequestLine httpRequestLine, final HttpRequestHeaderFields httpRequestHeaderFields, final HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeaderFields = httpRequestHeaderFields;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpMethod findMethod() {
        return httpRequestLine.getMethod();
    }

    public String findUri() {
        return httpRequestLine.findUri();
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

    public String findRequestBodyParam(String key) {
        return httpRequestBody.findQueryParam(key);
    }

    public String findHeaderField(String name) {
        return httpRequestHeaderFields.findField(name);
    }
}
