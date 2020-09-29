package http.request;

public class HttpRequest {

    private final HttpRequestLine httpRequestLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(final HttpRequestLine httpRequestLine, final HttpRequestHeader httpRequestHeader, final HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public String getMethod() {
        return this.httpRequestLine.getMethod();
    }

    public String getUrl() {
        return this.httpRequestLine.getUrl();
    }

    public String getHttpRequestHeaderByName(final String name) {
        return this.httpRequestHeader.getValue(name);
    }

    public String getHttpRequestParamsByName(final String name) {
        return this.httpRequestLine.getValue(name);
    }

    public String getHttpRequestBodyByName(final String name) {
        return this.httpRequestBody.getValue(name);
    }
}
