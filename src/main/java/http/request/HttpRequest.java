package http.request;

import http.HttpMethod;
import http.MediaType;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public boolean isContainExtension() {
        return MediaType.isContain(httpRequestLine.getUri());
    }

    public byte[] getHttpRequestBody() {
        return httpRequestBody.getBody();
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUri() {
        return httpRequestLine.getUri();
    }
}
