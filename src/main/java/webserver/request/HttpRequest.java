package webserver.request;

import webserver.request.requestline.HttpMethod;
import webserver.request.requestline.HttpRequestLine;
import webserver.request.requestline.QueryParams;

public class HttpRequest {

    private HttpRequestLine httpRequestLine;
    private HttpHeaderFields httpHeaderFields;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(final HttpRequestLine httpRequestLine, final HttpHeaderFields httpHeaderFields, final HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaderFields = httpHeaderFields;
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
}
