package http.request;

import http.HttpHeader;

import java.io.IOException;

public class HttpRequest {
    private HttpRequestStartLine httpRequestStartLine;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpHeader httpHeader, HttpBody httpBody) throws IOException {
        this.httpRequestStartLine = httpRequestStartLine;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public boolean hasParameters() {
        return httpRequestStartLine.hasParameters();
    }

    public boolean hasBody() {
        return !httpBody.equals(HttpBody.EMPTY_BODY);
    }

    public HttpMethodType getHttpMethod() {
        return httpRequestStartLine.getHttpMethodType();
    }

    public String getPath() {
        return httpRequestStartLine.getPath();
    }

    public String getHeader(String key) {
        return httpHeader.getHeader(key);
    }

    public String getParameter(String key) {
        return httpRequestStartLine.getParameter(key);
    }

    public HttpBody getBody() {
        return httpBody;
    }
}
